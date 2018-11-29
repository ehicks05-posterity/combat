package hicks.combat;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.text.DecimalFormat;

public class Log
{
    private static long lines;
    private static String messageQueue = "";
    private static DecimalFormat timeFormat = new DecimalFormat("000.00");
    private static DecimalFormat lineFormat = new DecimalFormat("000");

    public static void logInfo(String message)
    {
        logInfo(message, false);
    }

    public static void logInfo(String message, boolean flush)
    {
        lines++;
        String elapsedString = "000.00";

        BigDecimal simulationStart = GameState.getStartTime();
        if (simulationStart != null)
        {
            double elapsedTime = GameLogic.getElapsedTime(simulationStart).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
            elapsedString = timeFormat.format(elapsedTime);
        }

        message = lineFormat.format(lines) + " " + elapsedString + ": " + message + "\r\n";

        messageQueue += message;

        if (lines % 1000 == 0 || flush)
        {
            // System.out.print(message);
            try (PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter("log.txt", true))))
            {
                writer.print(messageQueue);
                messageQueue = "";
            }
            catch (IOException e)
            {
                System.out.println(e.getMessage());
            }
        }
    }
}
