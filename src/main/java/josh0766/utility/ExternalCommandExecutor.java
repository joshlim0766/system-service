package josh0766.utility;

import josh0766.dto.ExternalCommandExecutionResultDTO;
import josh0766.exception.InvalidArgumentException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ExternalCommandExecutor {
    public ExternalCommandExecutionResultDTO execute (String command, String[] args)
            throws IOException, InterruptedException {
        if (command == null) {
            throw new InvalidArgumentException("Command cannot be null.");
        }

        String[] cmd = new String[args.length + 1];
        cmd[0] = command;

        for (int i = 0; i < args.length; i++) {
            cmd[i + 1] = args[i];
        }

        ProcessBuilder processBuilder = new ProcessBuilder();
        processBuilder.command(cmd);
        ExternalCommandExecutionResultDTO result = null;

        try {
            processBuilder.redirectErrorStream(true);
            Process process = processBuilder.start();

            BufferedReader reader =
                    new BufferedReader(new InputStreamReader(process.getInputStream()));

            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }

            int exitCode = process.waitFor();

            result = ExternalCommandExecutionResultDTO.builder()
                    .exitCode(exitCode)
                    .executionResult(sb.toString())
                    .build();
        }
        catch (IOException ioe) {
            result = ExternalCommandExecutionResultDTO.builder()
                    .exitCode(-1)
                    .executionResult(ioe.getMessage())
                    .build();
        }
        catch (InterruptedException ie) {
            result = ExternalCommandExecutionResultDTO.builder()
                    .exitCode(-1)
                    .executionResult(ie.getMessage())
                    .build();
        }
        finally {
            return result;
        }
    }
}
