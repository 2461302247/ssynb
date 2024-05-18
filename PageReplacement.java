import java.util.*;

class PageReplacement {
    public static void main(String[] args) {
        // 页面访问序列
        int[] pageSequence = {1, 2, 3, 4, 5, 6, 7, 8, 1, 2, 3, 4, 5, 6, 7, 8, 3, 6, 9, 8};
        // 分配的内存块数（页面框）
        int numFrames = 4;

        System.out.println("FIFO Page Replacement Algorithm");
        int fifoPageFaults = calculatePageFaults("FIFO", pageSequence, numFrames);
        double fifoMissRate = calculateMissRate(fifoPageFaults, pageSequence.length);

        System.out.println("\nLRU Page Replacement Algorithm");
        int lruPageFaults = calculatePageFaults("LRU", pageSequence, numFrames);
        double lruMissRate = calculateMissRate(lruPageFaults, pageSequence.length);

        // 展示结果
        System.out.println("\nPage Faults and Miss Rate:");
        System.out.println("Algorithm\tPage Faults\tMiss Rate");
        System.out.printf("FIFO\t\t%d\t\t%.2f%%\n", fifoPageFaults, (fifoMissRate * 100));
        System.out.printf("LRU\t\t%d\t\t%.2f%%\n", lruPageFaults, (lruMissRate * 100));
    }

    private static int calculatePageFaults(String algorithm, int[] pageSequence, int numFrames) {
        if ("FIFO".equals(algorithm)) {
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
        } else if ("LRU".equals(algorithm)) {
            Deque<Integer> pages = new LinkedList<>();
            int pageFaults = 0;
            for (int page : pageSequence) {
                if (!pages.contains(page)) {
                    pageFaults++;
                    if (pages.size() == numFrames) {
                        pages.pollLast(); // Remove the least recently used (LRU) page
                    }
                    pages.push(page); // Add new page to the front (most recently used)
                } else {
                    // Move the accessed page to the front
                    pages.remove(page);
                    pages.push(page);
                }
            }
            return pageFaults;
        }
        return 0;
    }

    private static double calculateMissRate(int pageFaults, int totalPages) {
        return (double) pageFaults / totalPages;
    }
}