import java.util.HashMap;
import java.util.Map;

public class AttendanceRecord {
    private Student student;
    private Map<String, Boolean> attendance = new HashMap<>();

    public AttendanceRecord(Student student) {
        this.student = student;
    }

    public void toggle(String date) {
        attendance.put(date, !attendance.getOrDefault(date, false));
    }

    public boolean isPresent(String date) {
        return attendance.getOrDefault(date, false);
    }

    public Student getStudent() {
        return student;
    }
}
