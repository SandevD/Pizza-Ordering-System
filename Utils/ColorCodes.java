package Utils;

public class ColorCodes {
    public static final String RESET = "\u001B[0m";
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String YELLOW = "\u001B[33m";
    public static final String BLUE = "\u001B[34m";

    public static void printBannerTitle(String title, int width) {
        // Calculate padding
        int totalPadding = width - title.length() - 2;
        int paddingEachSide = totalPadding / 2;
        String padding = " ".repeat(Math.max(0, paddingEachSide));
        String adjustedTitle = "=".repeat(width);

        // Print title with padding
        System.out.println(ColorCodes.BLUE);
        System.out.println(adjustedTitle);
        System.out.println(padding + title + padding + (totalPadding % 2 == 1 ? " " : ""));
        System.out.println(adjustedTitle + ColorCodes.RESET + "\n");
    }

    public static void printReceiptTitle(String title, int width) {
        // Calculate padding
        int totalPadding = width - title.length() - 2;
        int paddingEachSide = totalPadding / 2;
        String padding = " ".repeat(Math.max(0, paddingEachSide));
        String adjustedTitle = "=".repeat(width);

        // Print title with padding
        System.out.println(ColorCodes.GREEN);
        System.out.println(adjustedTitle);
        System.out.println(padding + title + padding + (totalPadding % 2 == 1 ? " " : ""));
        System.out.println(adjustedTitle + ColorCodes.RESET + "\n");
    }

    public static void printSuccessMessage(String message, boolean isNextLine) {
        if (isNextLine) {
            System.out.println(ColorCodes.GREEN + message + ColorCodes.RESET);
        } else {
            System.out.print(ColorCodes.GREEN + message + ColorCodes.RESET);
        }
    }

    public static void printErrorMessage(String message, boolean isNextLine) {
        if (isNextLine) {
            System.out.println(ColorCodes.RED + message + ColorCodes.RESET);
        } else {
            System.out.print(ColorCodes.RED + message + ColorCodes.RESET);
        }
    }

    public static void printWarningMessage(String message, boolean isNextLine) {
        if (isNextLine) {
            System.out.println(ColorCodes.YELLOW + message + ColorCodes.RESET);
        } else {
            System.out.print(ColorCodes.YELLOW + message + ColorCodes.RESET);
        }
    }

    public static void divider(int width) {
        String divider = "-".repeat(width);
        System.out.println('\n' + ColorCodes.BLUE + divider + ColorCodes.RESET + "\n");
    }

    public static void printStep(int step, String message, boolean isNextLine) {
        if (isNextLine) {
            System.out.println(ColorCodes.GREEN + "Step " + step + ": " + ColorCodes.RESET + message);
        } else {
            System.out.print(ColorCodes.GREEN + "Step " + step + ": " + ColorCodes.RESET + message + " ");
        }
    }
}
