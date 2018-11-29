package hicks.combat;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

public class Metrics
{
    private static final int FRAMES_TO_AVERAGE = 30;
    private static Queue<BigDecimal> frameTimes = new ArrayBlockingQueue<>(30);
    public static long timeDiff;

    public static BigDecimal calculateFPS()
    {
        BigDecimal fps = BigDecimal.ZERO;

        BigDecimal timeDifference = new BigDecimal(timeDiff).divide(new BigDecimal("1000000"), 2, RoundingMode.HALF_UP);
        frameTimes.add(timeDifference);
        if (frameTimes.size() == FRAMES_TO_AVERAGE)
        {
            BigDecimal sum = BigDecimal.ZERO;
            for (BigDecimal frameTime : frameTimes)
                sum = sum.add(frameTime);

            BigDecimal averageFrameTime = sum.divide(new BigDecimal(FRAMES_TO_AVERAGE), 2, RoundingMode.HALF_UP);
            fps = new BigDecimal("1000").divide(averageFrameTime, 2, RoundingMode.HALF_UP);

            frameTimes.remove();
        }
        return fps;
    }
}
