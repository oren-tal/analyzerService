package org.example;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@RestController
public class MockAnalyzerService {

    private static final String GRAPH_INPUT_FILE_PATH = "C:/Users/Oren/OneDrive - Technion/backup/Desktop/project/graphInput.txt"; // Update with the actual path

    @PostMapping("/analyze")
    public ResponseEntity<String> analyze(@RequestParam("file") MultipartFile jarFile) {
        System.out.println("Received a request to /analyze");
        try {
            // Save the uploaded jar file (for demonstration purposes, not actually used)
            Path tempDir = Files.createTempDirectory("");
            Path tempFile = tempDir.resolve(jarFile.getOriginalFilename());
            System.out.println("Saving uploaded JAR file to: " + tempFile.toString());
            jarFile.transferTo(tempFile.toFile());

            // Read the content of graphInput.txt
            File graphInputFile = new File(GRAPH_INPUT_FILE_PATH);
            if (graphInputFile.exists()) {
                System.out.println("Reading contents of: " + GRAPH_INPUT_FILE_PATH);
                String output = new String(Files.readAllBytes(graphInputFile.toPath()));
                System.out.println("Returning content of graphInput.txt");
                return ResponseEntity.ok(output);
            } else {
                System.out.println("Graph input file not found at: " + GRAPH_INPUT_FILE_PATH);
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Graph input file not found");
            }
        } catch (IOException e) {
            System.out.println("Error processing the file: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error processing the file");
        }
    }
}
