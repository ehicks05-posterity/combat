package hicks.combat;

import hicks.combat.entities.Barracks;
import hicks.combat.entities.Knight;
import hicks.combat.entities.Unit;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class NameLogic
{
    private static List<String> firstNames;
    private static List<String> lastNames;

    public static void init()
    {

    }

    public static String generateName(Unit unit)
    {
        Random random = new Random();

        String fullName = "whatever";
        if (unit instanceof Knight) fullName = "Sir " + fullName;
        if (unit instanceof Barracks) fullName = "Barracks";
        return fullName;
    }
}
