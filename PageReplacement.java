import java.util.*;

class PageReplacement {
    public static void main(String[] args) {
        // 页面访问序列
        int[] pageSequence = {1, 2, 3, 4, 5, 6, 7, 8, 1, 2, 3, 4, 5, 6, 7, 8, 3, 6, 9, 8};
        // 分配的内存块数（页面框）
        int numFrames = 4;

        System.out.println("FIFO Page Replacement Algorithm");
        int fifoPageFaults = calculatePageFaultsFIFO(pageSequence, numFrames);
        double fifoMissRate = calculateMissRate(pageSequence.length, fifoPageFaults);

        System.out.println("\nLRU Page Replacement Algorithm");
        int lruPageFaults = calculatePageFaultsLRU(pageSequence, numFrames);
        double lruMissRate = calculateMissRate(pageSequence.length, lruPageFaults);

        // 展示结果
        System.out.println("\nPage Faults and Miss Rate:");
        System.out.println("Algorithm\tPage Faults\tMiss Rate");
        System.out.printf("FIFO\t\t%d\t\t%.2f%%\n", fifoPageFaults, (fifoMissRate * 100));
        System.out.printf("LRU\t\t%d\t\t%.2f%%\n", lruPageFaults, (lruMissRate * 100));
    }

    private static int calculatePageFaultsFIFO(int[] pageSequence, int numFrames) {
        Queue<Integer> pages = new LinkedList<>();
        int pageFaults = 0;
        for (int page : pageSequence) {
            if (!pages.contains(page)) {
                if (pages.size() == numFrames) {
                    pages.poll(); // Remove the oldest page
                }
                pages.add(page);
                pageFaults++;
            }
        }
        return pageFaults;
    }

    private static int calculatePageFaultsLRU(int[] pageSequence, int numFrames) {
        Deque<Integer> pages = new LinkedList<>();
        int pageFaults = 0;

        for (int page : pageSequence) {
            if (!pages.contains(page)) {
                if (pages.size() == numFrames) {
                    pages.poll(); // Remove the least recently used (LRU) page
                }
                pages.push(page); // Add new page to the front (most recently used)
                pageFaults++;
            } else {
                // Page is already in the set, move it to the front (most recently used)
                pages.remove(page);
                pages.push(page);
            }
        }
        return pageFaults;
    }

    private static double calculateMissRate(int totalPages, int pageFaults) {
        return (double) pageFaults / totalPages;
    }
}
