import java.util.*;

class PageReplacement {
    public static void main(String[] args) {
        // 页面访问序列
        int[] pageSequence = {1, 2, 3, 4, 5, 6, 7, 8, 1, 2, 3, 4, 5, 6, 7, 8, 3, 6, 9, 8};
        // 分配的内存块数（页面框）
        int numFrames = 4;

        System.out.println("FIFO Page Replacement Algorithm");
        int fifoPageFaults = calculatePageFaults("FIFO", pageSequence, numFrames);
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
        }
        return 0;
    }

    private static int calculatePageFaultsLRU(int[] pageSequence, int numFrames) {
        Map<Integer, Integer> pages = new LinkedHashMap<>(numFrames, 0.75f, true) {
            protected boolean removeEldestEntry(Map.Entry<Integer, Integer> eldest) {
                return size() > numFrames;
            }
        };
        int pageFaults = 0;

        for (int page : pageSequence) {
            if (!pages.containsKey(page)) {
                pages.put(page, 1); // 使用键值对存储页面，值为访问次数
                pageFaults++;
            } else {
                pages.put(page, pages.get(page) + 1); // 更新页面的访问次数
            }
        }
        return pageFaults;
    }

    private static double calculateMissRate(int totalPages, int pageFaults) {
        return (double) pageFaults / totalPages;
    }
}