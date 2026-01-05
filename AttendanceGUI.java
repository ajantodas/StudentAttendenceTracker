import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.io.FileWriter;
import java.time.LocalDate;
import java.util.regex.Pattern;

public class AttendanceGUI extends JFrame {

    private AttendanceManager manager = new AttendanceManager();
    private DefaultTableModel model;
    private JTextField idField;
    private JLabel presentLbl, absentLbl;
    private String today = LocalDate.now().toString();

    // 🎨 COLORS
    private final Color BG_COLOR = new Color(245, 247, 250);
    private final Color HEADER_COLOR = new Color(33, 150, 243);
    private final Color BTN_COLOR = new Color(76, 175, 80);
    private final Color EXPORT_COLOR = new Color(255, 152, 0);
    private final Color TEXT_COLOR = new Color(33, 33, 33);

    public AttendanceGUI() {
        setTitle("Attendance Tracker - CSE 59th(H)");
        setSize(950, 520);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(BG_COLOR);

        // 🔷 Header
        JLabel title = new JLabel("Student Attendance System - CSE 59th(H)", JLabel.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 22));
        title.setForeground(Color.WHITE);
        title.setOpaque(true);
        title.setBackground(HEADER_COLOR);
        title.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(title, BorderLayout.NORTH);

        // 🔷 Table
        model = new DefaultTableModel(new String[]{"ID", "Name", "Status"}, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };

        JTable table = new JTable(model);
        table.setRowHeight(30);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        table.setBackground(Color.WHITE);
        table.setForeground(TEXT_COLOR);

        JTableHeader th = table.getTableHeader();
        th.setBackground(new Color(63, 81, 181));
        th.setForeground(Color.WHITE);
        th.setFont(new Font("Segoe UI", Font.BOLD, 14));

        add(new JScrollPane(table), BorderLayout.CENTER);

        loadStudents();

        // 🔷 Bottom Panel
        JPanel bottom = new JPanel();
        bottom.setBackground(BG_COLOR);

        idField = new JTextField(12);
        idField.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        JButton markBtn = new JButton("Mark / Unmark");
        styleButton(markBtn, BTN_COLOR);

        JButton exportBtn = new JButton("Export CSV");
        styleButton(exportBtn, EXPORT_COLOR);

        presentLbl = new JLabel();
        absentLbl = new JLabel();
        presentLbl.setFont(new Font("Segoe UI", Font.BOLD, 14));
        absentLbl.setFont(new Font("Segoe UI", Font.BOLD, 14));
        presentLbl.setForeground(new Color(76, 175, 80));
        absentLbl.setForeground(new Color(244, 67, 54));

        updateCount();

        bottom.add(new JLabel("📅 Date: " + today));
        bottom.add(new JLabel("Student ID:"));
        bottom.add(idField);
        bottom.add(markBtn);
        bottom.add(exportBtn);
        bottom.add(presentLbl);
        bottom.add(absentLbl);

        add(bottom, BorderLayout.SOUTH);

        markBtn.addActionListener(e -> mark());
        exportBtn.addActionListener(e -> exportCSV());

        setVisible(true);
    }

    // 🎨 Button Style
    private void styleButton(JButton btn, Color color) {
        btn.setBackground(color);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
    }

    private void loadStudents() {
        String[][] students = {
            {"232-115-283","MD. Salman"},{"232-115-285","Sourov Chandra Shill"},
            {"232-115-286","Afrina Akhter"},{"232-115-287","Mostafa Morshed Rafat"},
            {"232-115-288","Sharna Akhter"},{"232-115-291","Suraiya Rahman Urmi"},
            {"232-115-292","Urmi Chakraborty"},{"232-115-294","Ajanto Lal Das"},
            {"232-115-298","Itkan Hanif"},{"232-115-299","Ibnul Hanif"},
            {"232-115-302","Sabbir Ahmed"},{"232-115-303","Nahida Akhter Jenifa"},
            {"232-115-304","Avisehk Chandra Das"},{"232-115-305","Israth Mahzabin Khan"},
            {"232-115-307","Zareen Anzum"},{"232-115-308","Chondrima Rahman"},
            {"232-115-309","Anika Anzum Mim"},{"232-115-312","Chaity Upadhyay"},
            {"232-115-313","Khadizatul Kobra"},{"232-115-314","Sadia Sultana"},
            {"232-115-316","Safwan Safat"},{"232-115-317","Umme Mahria Hasan"},
            {"232-115-318","Hridoy Kumar Singha"},{"231-115-159","Maaj Bin Moin"},
            {"231-115-266","Imran Hosen"},{"231-115-327","Md Zawadul Islam Sobhany"}
        };

        for (String[] s : students) {
            AttendanceRecord ar = new AttendanceRecord(new Student(s[0], s[1]));
            manager.add(ar);
            model.addRow(new Object[]{s[0], s[1], "Absent"});
        }
    }

    private void mark() {
        String id = idField.getText().trim();
        if (!Pattern.matches("\\d{3}-\\d{3}-\\d{3}", id)) {
            JOptionPane.showMessageDialog(this, "Invalid ID format");
            return;
        }

        for (int i = 0; i < manager.getRecords().size(); i++) {
            AttendanceRecord ar = manager.getRecords().get(i);
            if (ar.getStudent().getId().equals(id)) {
                ar.toggle(today);
                model.setValueAt(ar.isPresent(today) ? "Present" : "Absent", i, 2);
                updateCount();
                idField.setText("");
                return;
            }
        }
        JOptionPane.showMessageDialog(this, "Student not found!");
    }

    private void updateCount() {
        presentLbl.setText(" Present: " + manager.presentCount(today));
        absentLbl.setText(" Absent: " + manager.absentCount(today));
    }

    private void exportCSV() {
        try (FileWriter fw = new FileWriter("attendance_" + today + ".csv")) {
            fw.write("ID,Name,Status\n");
            for (int i = 0; i < model.getRowCount(); i++) {
                fw.write(model.getValueAt(i,0)+","+
                         model.getValueAt(i,1)+","+
                         model.getValueAt(i,2)+"\n");
            }
            JOptionPane.showMessageDialog(this, "CSV Exported Successfully!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Export Failed!");
        }
    }
}
