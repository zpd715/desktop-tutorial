import java.time.LocalDateTime;
import java.util.*;

public class BikeService {
    private BikeDatabase bikeDatabase;
    private Queue<BikeRequest> bikeRequestQueue;
    private Deque<ERyderLog> systemLogStack;

    public BikeService(BikeDatabase bikeDatabase, Deque<ERyderLog> systemLogStack) {
        this.bikeDatabase = bikeDatabase;
        this.bikeRequestQueue = new ArrayDeque<>();
        this.systemLogStack = systemLogStack;
    }

    public List<Bike> findAvailableBikes(String location) {
        return bikeDatabase.findAvailableBikesByLocation(location);
    }

    public boolean validateLocation(String location) {
        return location != null && !location.trim().isEmpty();
    }

    public boolean reserveBike(String bikeId, String userEmail, String location) {
        Bike bike = bikeDatabase.findBikeById(bikeId);
        if (bike != null && bike.isAvailable()) {
            bike.setAvailable(false);
            bikeDatabase.updateBike(bike);
            // 记录日志：自行车租借成功
            String logId = "BR" + System.currentTimeMillis();
            ERyderLog log = new ERyderLog(logId, "Bike " + bikeId + " rented by " + userEmail + " from " + location, LocalDateTime.now());
            systemLogStack.push(log);
            System.out.println("Bike " + bikeId + " reserved.");
            return true;
        } else {
            // 自行车不可用，将请求加入队列
            BikeRequest request = new BikeRequest(userEmail, location, LocalDateTime.now());
            bikeRequestQueue.add(request);
            System.out.println("Bike not available. Request added to queue.");
            return false;
        }
    }

    public boolean releaseBike(String bikeId) {
        Bike bike = bikeDatabase.findBikeById(bikeId);
        if (bike != null && !bike.isAvailable()) {
            bike.setAvailable(true);
            bikeDatabase.updateBike(bike);
            System.out.println("Bike " + bikeId + " released.");

            // 检查队列中是否有等待的请求
            if (!bikeRequestQueue.isEmpty()) {
                BikeRequest nextRequest = bikeRequestQueue.poll();
                System.out.println("Assigning bike " + bikeId + " to next waiting user: " + nextRequest.getUserEmail() +
                                   " at location " + nextRequest.getLocation());
                // 可选：自动为该用户开始租赁，但为避免复杂，仅打印信息
            }
            return true;
        }
        System.out.println("Bike not in use or not found.");
        return false;
    }

    // 供AdminPanel调用的队列查看方法
    public Queue<BikeRequest> getBikeRequestQueue() {
        return bikeRequestQueue;
    }

    // 移除队列第一个元素（Update Queue）
    public void removeFirstRequest() {
        if (!bikeRequestQueue.isEmpty()) {
            BikeRequest removed = bikeRequestQueue.poll();
            System.out.println("Removed request from queue: " + removed);
        } else {
            System.out.println("Queue is empty.");
        }
    }
}