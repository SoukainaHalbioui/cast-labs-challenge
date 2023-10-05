package com.castLabs.file;

import com.castLabs.file.dtos.StructuredFile;
import com.castLabs.file.exception.InvalidUrlException;
import com.castLabs.file.exception.UnreadableFileException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static com.castLabs.file.FileController.FILE_ANALYSIS_ENDPOINT;
import static com.castLabs.file.exception.InvalidUrlException.INVALID_URL_ERROR_MESSAGE;
import static com.castLabs.file.exception.UnreadableFileException.UNREADABLE_FILE_ERROR_MESSAGE;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class FileControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FileService fileService;

    private final String TEST_FILE_URL = "http://demo.castlabs.com/tmp/text0.mp4";

    private final String INVALID_URL = "http://invalid-url.mp4";

    @Test
    public void analyzeFileSucceeds() throws Exception {
        // given
        Box box = Box.builder().type("BOOP").contentSize(8).totalSize(16).build();
        StructuredFile structuredFile = new StructuredFile(TEST_FILE_URL, List.of(box));
        when(fileService.analyzeFile(TEST_FILE_URL)).thenReturn(structuredFile);

        // when
        ResultActions actions = mockMvc.perform(get(FILE_ANALYSIS_ENDPOINT).param("file_url", TEST_FILE_URL));

        // then
        actions.andExpect(status().isOk())
                .andExpect(jsonPath("$.path", equalTo(TEST_FILE_URL)))
                .andExpect(jsonPath("$.content[0].type", equalTo("BOOP")))
                .andExpect(jsonPath("$.content[0].totalSize", equalTo(16)))
                .andExpect(jsonPath("$.content[0].contentSize", equalTo(8)));
        verify(fileService, times(1)).analyzeFile(TEST_FILE_URL);
    }

    @Test
    public void analyzeFileFailsDueToInvalidUrl() throws Exception {
        // given
        when(fileService.analyzeFile(INVALID_URL)).thenThrow(new InvalidUrlException());

        // when
        ResultActions actions = mockMvc.perform(get(FILE_ANALYSIS_ENDPOINT).param("file_url", INVALID_URL));

        // then
        actions.andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", equalTo(INVALID_URL_ERROR_MESSAGE)));
        verify(fileService, times(1)).analyzeFile(INVALID_URL);
    }

    @Test
    public void analyzeFileFailsDueToUnreadableFile() throws Exception {
        // given
        when(fileService.analyzeFile(TEST_FILE_URL)).thenThrow(new UnreadableFileException());

        // when
        ResultActions actions = mockMvc.perform(get(FILE_ANALYSIS_ENDPOINT).param("file_url", TEST_FILE_URL));

        // then
        actions.andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", equalTo(UNREADABLE_FILE_ERROR_MESSAGE)));
        verify(fileService, times(1)).analyzeFile(TEST_FILE_URL);
    }
}
