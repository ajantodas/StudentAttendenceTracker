import java.util.ArrayList;

public class AttendanceManager {
    private ArrayList<AttendanceRecord> records = new ArrayList<>();

    public void add(AttendanceRecord r) {
        records.add(r);
    }

    public ArrayList<AttendanceRecord> getRecords() {
        return records;
    }

    public int presentCount(String date) {
        int c = 0;
        for (AttendanceRecord r : records)
            if (r.isPresent(date)) c++;
        return c;
    }

    public int absentCount(String date) {
        return records.size() - presentCount(date);
    }
}
