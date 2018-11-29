package hicks.combat;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;

public class TileSet
{
    private static Map<String, BufferedImage> tiles = new HashMap<>();

    private static List<String> tileNames = Arrays.asList(
            "CCCC", "DDDD", "GGGG", "WWWW", "XXX1", "XXX2", "XXX3", "XXX4", "GGDD", "GDDD",
            "RRR1", "RRGG", "GGRR", "RRR2", "GRGR", "RGRG", "OIL1", "DDGG", "DGDG", "DDDG",
            "CGCC", "GCCC", "CCCG", "CCGC", "GGCC", "CCGG", "CGCG", "GCGC", "DGDD", "DDGD",
            "WGWG", "GWGW", "WWGG", "GGWW", "WGWW", "GWWW", "WWWG", "WWGW", "GDGD", "NULL");

    private static BufferedImage loadTileSet()
    {
        try
        {
            return ImageIO.read(new File("ass\\tiles.bmp"));
        }
        catch (IOException e)
        {
            Log.logInfo(e.getMessage());
        }

        return null;
    }

    private static void createTilesList()
    {
        BufferedImage tileSet = loadTileSet();

        BufferedImage bi;
        int x = 0;
        int y = 0;

        for (int i = 0; i < 40; i++)
        {
            if (i > 0 && i % 10 == 0)
            {
                x = 0;
                y += 33;
            }

            bi = tileSet.getSubimage(x, y, 32, 32);
            tiles.put(tileNames.get(i), bi);
            x += 33;

        }
    }

    public static BufferedImage buildMap()
    {
        createTilesList();

        BufferedImage terrain = new BufferedImage(Init.WORLD_WIDTH, Init.WORLD_HEIGHT, BufferedImage.TYPE_INT_RGB);

        int iterations = (Init.WORLD_WIDTH / 32) * (Init.WORLD_HEIGHT / 32);
        int x = 0;
        int y = 0;
        int[] rgbArray = new int[32*32];

        // fill map with grass
        for (int i = 0; i < iterations; i++)
        {
            if (x >= Init.WORLD_WIDTH)
            {
                x = 0;
                y += 32;
            }

            BufferedImage tile = tiles.get("GGGG");

            rgbArray = tile.getRGB(0, 0, 32, 32, rgbArray, 0, 32);
            terrain.setRGB(x, y, 32, 32, rgbArray, 0, 32);

            x += 32;
        }

        // add a path
        int tileColumns = Init.WORLD_WIDTH / 32;
        int tileRows    = Init.WORLD_HEIGHT / 32;
        Random gen = new Random();

        int randomColumn = gen.nextInt(tileColumns);

        x = 0;
        y = 0;
        for (int i = 0; i < iterations; i++)
        {
            if (x >= Init.WORLD_WIDTH)
            {
                x = 0;
                y += 32;
                if (y == Init.WORLD_HEIGHT) break;
            }

            if (x == (randomColumn - 1) * 32)
            {
                BufferedImage tile = tiles.get("DDDD");

                rgbArray = tile.getRGB(0, 0, 32, 32, rgbArray, 0, 32);
                terrain.setRGB(x, y, 32, 32, rgbArray, 0, 32);
            }

            x += 32;
        }

        return terrain;
    }
}
