package com.castLabs.file;

import com.castLabs.file.dtos.StructuredFile;
import com.castLabs.file.exception.InvalidUrlException;
import com.castLabs.file.exception.UnreadableFileException;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigInteger;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class FileService {

    private final List<String> parentBoxes = List.of("MOOF", "TRAF");

    public StructuredFile analyzeFile(String fileUrl) {
        byte[] bytes = readFileBytes(fileUrl);
        List<Box> fileContent = getBoxes(bytes);
        return new StructuredFile(fileUrl, fileContent);
    }

    private List<Box> getBoxes(byte[] bytes) {
        List<Box> boxes = new ArrayList<>();

        int index = 0;
        while (index < bytes.length) {
            int boxSize = getBoxSize(bytes, index);
            String boxType = getBoxType(bytes, index);
            Box box = Box.builder().type(boxType).totalSize(boxSize).contentSize(boxSize - 8).build();
            if (parentBoxes.contains(boxType)) {
                byte[] boxContentBytes = Arrays.copyOfRange(bytes, index + 8, index + boxSize - 8);
                List<Box> children = getBoxes(boxContentBytes);
                box.setContent(children);
            } else {
                box.setContent(new ArrayList<>());
            }
            boxes.add(box);
            index = index + boxSize;
        }
        return boxes;
    }

    private byte[] readFileBytes(String fileUrl) {
        try {
            URL url = new URL(fileUrl);
            return IOUtils.toByteArray(url);
        } catch (MalformedURLException ex) {
            throw new InvalidUrlException();
        } catch (IOException ex) {
            throw new UnreadableFileException();
        }

    }

    private int getBoxSize(byte[] bytes, int boxStart) {
        byte[] boxSizeInBytes = Arrays.copyOfRange(bytes, boxStart, boxStart + 4);
        return new BigInteger(boxSizeInBytes).intValue();
    }

    private String getBoxType(byte[] bytes, int boxStart) {
        byte[] boxTypeInBytes = Arrays.copyOfRange(bytes, boxStart + 4, boxStart + 8);
        return new String(boxTypeInBytes, StandardCharsets.ISO_8859_1).toUpperCase();
    }
}
