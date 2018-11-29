package hicks.combat;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

public class MapBuilder
{
    private static final int TILE_SIZE = 1;

    private static int[][] buildTerrain(int WORLD_WIDTH, int WORLD_HEIGHT)
    {
        Random gen = new Random();

        int[][] terrain = new int[WORLD_WIDTH][WORLD_HEIGHT];
        for (int i = 0; i < WORLD_WIDTH; i += TILE_SIZE)
        {
            for (int j = 0; j < WORLD_HEIGHT; j += TILE_SIZE)
            {
                if (gen.nextFloat() > 0.9)
                    terrain[i][j] = 0;
                else
                    terrain[i][j] = 1;
            }
        }
        return terrain;
    }

    public static BufferedImage buildTerrainImage(int WORLD_WIDTH, int WORLD_HEIGHT)
    {
        int[][] terrain = buildTerrain(WORLD_WIDTH, WORLD_HEIGHT);

        BufferedImage bf = new BufferedImage(WORLD_WIDTH, WORLD_HEIGHT, BufferedImage.TYPE_INT_RGB);

        for (int i = 0; i < WORLD_WIDTH; i += TILE_SIZE)
        {
            for (int j = 0; j < WORLD_HEIGHT; j += TILE_SIZE)
            {
                if (terrain[i][j] == 0)
                    bf.setRGB(i, j, new Color(40, 60, 16).getRGB());
                else
                    bf.setRGB(i, j, new Color(36, 36, 16).getRGB());
            }
        }
        return bf;
    }
}
