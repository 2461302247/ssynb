import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PageReplacementSimulation {

    private static class PageFaultInfo {
        int pageFaults;
        double missRatio;

        PageFaultInfo() {
            pageFaults = 0;
            missRatio = 0.0;
        }

        void addPageFault() {
            pageFaults++;
        }

        void addReference() {
            // 更新缺页率，假设总的引用次数是固定的
            missRatio = (double) pageFaults / 100;
        }
    }

    public static void simulateFIFO(List<Integer> pageSequence, int memorySize) {
        List<Integer> memory = new ArrayList<>();
        PageFaultInfo info = new PageFaultInfo();
        int pageFaults = 0;

        for (int page : pageSequence) {
            if (!memory.contains(page)) {
                pageFaults++;
                if (memory.size() == memorySize) {
                    // 移除队列头部的页面
                    memory.remove(0);
                }
                memory.add(page);
            }
        }

        info.pageFaults = pageFaults;
        info.addReference();
        System.out.println("FIFO: Page faults = " + pageFaults + ", Miss ratio = " + info.missRatio);
    }

    public static void simulateLRU(List<Integer> pageSequence, int memorySize) {
        List<Integer> memory = new ArrayList<>();
        Map<Integer, Integer> pageAccessTime = new HashMap<>();
        PageFaultInfo info = new PageFaultInfo();
        int pageFaults = 0;

        for (int page : pageSequence) {
            if (!memory.contains(page)) {
                pageFaults++;
                if (memory.size() == memorySize) {
                    // 移除最老的页面，即访问时间最小的页面
                    int oldestPage = -1;
                    int minAccessTime = Integer.MAX_VALUE;
                    for (int p : memory) {
                        if (pageAccessTime.get(p) < minAccessTime) {
                            oldestPage = p;
                            minAccessTime = pageAccessTime.get(p);
                        }
                    }
                    memory.remove(Integer.valueOf(oldestPage));
                    pageAccessTime.remove(oldestPage);
                }
                memory.add(page);
                pageAccessTime.put(page, pageAccessTime.size() + 1);
            }
            pageAccessTime.put(page, pageAccessTime.getOrDefault(page, 0) + 1);
        }

        info.pageFaults = pageFaults;
        info.addReference();
        System.out.println("LRU: Page faults = " + pageFaults + ", Miss ratio = " + info.missRatio);
    }

    public static void main(String[] args) {
        List<Integer> pageSequence = List.of(0, 1, 1, 2, 0, 3, 4, 1, 2, 3, 4, 0, 1, 2, 3, 4); // 页面引用序列
        int memorySize = 4; // 内存块数

        simulateFIFO(pageSequence, memorySize);
        simulateLRU(pageSequence, memorySize);
    }
}