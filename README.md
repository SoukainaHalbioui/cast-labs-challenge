# CastLabs Java Coding Challenge

#### How to start the application
1. clone the repository (this step can be skipped if you are downloading the project zip directly from the submission link)
2. once cloned locally, open it in an IDE of your choice (preferably Intellij)
3. navigate to the file `src/main/java/com/castLabs/Application.java`, right click to see the options and then click on `run Application.main()`
4. make sure the application has started on the IDE's terminal. The tomcat server will be accessible on the default port 8080

#### The endpoint
`GET http://localhost:8080/file/analysis?file_url=`.
The URL of the file to be analyzed should be supplied to the request param `file_url`. Example: `GET http://localhost:8080/file/analysis?file_url=https://demo.castlabs.com/tmp/text0.mp4`

#### Usage
You can call the endpoint above to analyze the file specified in the assignment description as shown below:
1. make the sure the application is up and running (see the section on how to start the application above)
2. open a terminal
3. execute the command `curl http://localhost:8080/file/analysis?file_url=https://demo.castlabs.com/tmp/text0.mp4`
4. you should see the following JSON response that describes the structure of the file:

```
{
  "path": "https://demo.castlabs.com/tmp/text0.mp4",
  "content": [
    {
      "type": "MOOF",
      "totalSize": 181,
      "contentSize": 173,
      "content": [
        {
          "type": "MFHD",
          "totalSize": 16,
          "contentSize": 8,
          "content": []
        },
        {
          "type": "TRAF",
          "totalSize": 157,
          "contentSize": 149,
          "content": [
            {
              "type": "TFHD",
              "totalSize": 24,
              "contentSize": 16,
              "content": []
            },
            {
              "type": "TRUN",
              "totalSize": 20,
              "contentSize": 12,
              "content": []
            },
            {
              "type": "UUID",
              "totalSize": 44,
              "contentSize": 36,
              "content": []
            },
            {
              "type": "UUID",
              "totalSize": 61,
              "contentSize": 53,
              "content": []
            }
          ]
        }
      ]
    },
    {
      "type": "MDAT",
      "totalSize": 17908,
      "contentSize": 17900,
      "content": []
    }
  ]
}
```
