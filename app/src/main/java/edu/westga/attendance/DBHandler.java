package edu.westga.attendance;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import edu.westga.attendance.model.Attendance;
import edu.westga.attendance.model.Course;
import edu.westga.attendance.model.Student;
import edu.westga.attendance.model.StudentInCourse;

/**
 * Created by Wayne on 4/9/2016.
 *
 * Creates DB
 */
public class DBHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "courseattendance.db";
    public static final String TABLE_STUDENT = "student";
    public static final String TABLE_COURSE = "course";
    public static final String TABLE_STUDENTINCOURSE = "studentincourse";
    public static final String TABLE_ATTENDANCE = "attendance";

    public static final String COLUMN_STUDENTID = "studentid";
    public static final String COLUMN_FIRSTNAME = "firstname";
    public static final String COLUMN_LASTNAME = "lastname";
    public static final String COLUMN_COURSEID = "courseid";
    public static final String COLUMN_COURSENAME = "coursename";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_DATE = "classdate";
    public static final String COLUMN_PRESENT = "present";

    public DBHandler(Context context, String name,
                       SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_STUDENTS_TABLE = "CREATE TABLE " +
                TABLE_STUDENT + "("
                + COLUMN_STUDENTID + " INTEGER PRIMARY KEY," + COLUMN_FIRSTNAME
                + " TEXT," + COLUMN_LASTNAME + " TEXT" + ")";
        db.execSQL(CREATE_STUDENTS_TABLE);

        String CREATE_COURSES_TABLE = "CREATE TABLE " +
                TABLE_COURSE + "("
                + COLUMN_COURSEID + " INTEGER PRIMARY KEY," + COLUMN_COURSENAME
                + " TEXT)";
        db.execSQL(CREATE_COURSES_TABLE);

        String CREATE_STUDENTINCOURSE_TABLE = "CREATE TABLE " +
                TABLE_STUDENTINCOURSE + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY,"
                + COLUMN_STUDENTID + " INTEGER, "
                + COLUMN_COURSEID + " INTEGER, "
                + "FOREIGN KEY(" + COLUMN_STUDENTID + ") REFERENCES "
                + TABLE_STUDENT + "(studentid), "
                + "FOREIGN KEY(" + COLUMN_COURSEID + ") REFERENCES "
                + TABLE_COURSE + "(courseid) "
                + " )";
        db.execSQL(CREATE_STUDENTINCOURSE_TABLE);

        String CREATE_ATTENDANCE_TABLE = "CREATE TABLE " +
                TABLE_ATTENDANCE + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY, "
                + "studentcourseid INTEGER, "
                + COLUMN_DATE + " TEXT, "
                + COLUMN_PRESENT + " Integer, "
                + "FOREIGN KEY(studentcourseid) REFERENCES "
                + TABLE_STUDENTINCOURSE + "(id))";

        db.execSQL(CREATE_ATTENDANCE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_STUDENT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_COURSE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_STUDENTINCOURSE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ATTENDANCE);
        onCreate(db);
    }

    public void addStudent(Student student) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_FIRSTNAME, student.getFirstName());
        values.put(COLUMN_LASTNAME, student.getLastName());

        SQLiteDatabase db = this.getWritableDatabase();

        db.insert(TABLE_STUDENT, null, values);
        db.close();
    }

    public void addCourse(Course course) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_COURSENAME, course.getCourseName());

        SQLiteDatabase db = this.getWritableDatabase();

        db.insert(TABLE_COURSE, null, values);
        db.close();
    }

    public void addStudentInCourse(StudentInCourse studentInCourse) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_STUDENTID, studentInCourse.getStudent().getStudentID());
        values.put(COLUMN_COURSEID, studentInCourse.getCourse().getCourseID());

        SQLiteDatabase db = this.getWritableDatabase();

        db.insert(TABLE_STUDENTINCOURSE, null, values);
        db.close();
    }

    public void addAttendance(Attendance attendance) {
        ContentValues values = new ContentValues();
        values.put("studentcourseid", attendance.getStudentInCourse().getId());
        values.put(COLUMN_DATE, attendance.getDate());
        values.put(COLUMN_PRESENT, attendance.getPresent());

        SQLiteDatabase db = this.getWritableDatabase();

        db.insert(TABLE_ATTENDANCE, null, values);
        db.close();
    }

    public int checkIfStudentExists(String firstName, String lastName) {
        String query = "Select COUNT(*) FROM " + TABLE_STUDENT + " WHERE " + COLUMN_FIRSTNAME + " =  \"" + firstName + "\" COLLATE NOCASE"
                + " AND " + COLUMN_LASTNAME + " =  \"" + lastName + "\" COLLATE NOCASE";

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(query, null);
        int count;
        if (cursor.moveToFirst()) {
            cursor.moveToFirst();
            count = Integer.parseInt(cursor.getString(0));
            cursor.close();
        } else {
            count = 0;
        }

        db.close();
        return count;
    }

    public int checkIfCourseExists(String courseName) {
        String query = "Select COUNT(*) FROM " + TABLE_COURSE + " WHERE " + COLUMN_COURSENAME + " =  \"" + courseName + "\" COLLATE NOCASE";

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(query, null);
        int count;
        if (cursor.moveToFirst()) {
            cursor.moveToFirst();
            count = Integer.parseInt(cursor.getString(0));
            cursor.close();
        } else {
            count = 0;
        }

        db.close();
        return count;
    }

    public int checkIfStudentInCourseExists(Student student, Course course) {
        String query = "Select COUNT(*) FROM " + TABLE_STUDENTINCOURSE
                + " WHERE " + COLUMN_STUDENTID + " =  \"" + student.getStudentID() + "\""
                + " AND " + COLUMN_COURSEID + " =  \"" + course.getCourseID() + "\"";

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(query, null);
        int count;
        if (cursor.moveToFirst()) {
            cursor.moveToFirst();
            count = Integer.parseInt(cursor.getString(0));
            cursor.close();
        } else {
            count = 0;
        }

        db.close();
        return count;
    }

    public int checkIfAttendanceExists(Attendance attendance) {
        String query = "Select COUNT(*) FROM " + TABLE_ATTENDANCE
                + " WHERE studentcourseid =  \"" + attendance.getStudentInCourse().getId() + "\""
                + " AND " + COLUMN_DATE + " =  \"" + attendance.getDate() + "\"";

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(query, null);
        int count;
        if (cursor.moveToFirst()) {
            cursor.moveToFirst();
            count = Integer.parseInt(cursor.getString(0));
            cursor.close();
        } else {
            count = 0;
        }

        db.close();
        return count;
    }

    public ArrayList<Attendance> getAttendanceForCourseDate(Course course, String date) {
        String query = "Select attendance.id, attendance.present, attendance.classdate, studentincourse.id, "
                + " student.studentid, student.firstname, student.lastname, course.coursename FROM " + TABLE_ATTENDANCE
                + " LEFT JOIN studentincourse ON attendance.studentcourseid = studentincourse.id"
                + " JOIN student ON studentincourse.studentid = student.studentid"
                + " JOIN course ON studentincourse.courseid = course.courseid"
                + " WHERE studentincourse.courseid =  \"" + course.getCourseID() + "\""
                + " AND attendance.classdate =  \"" + date + "\"";

        ArrayList<Attendance> attendances = new ArrayList<>();

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(query, null);

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    Attendance attendance = new Attendance();
                    attendance.setId(Integer.parseInt(cursor.getString(0)));
                    attendance.setPresent(Integer.parseInt(cursor.getString(1)));
                    attendance.setDate(cursor.getString(2));

                    StudentInCourse studentInCourse = new StudentInCourse();
                    studentInCourse.setID(Integer.parseInt(cursor.getString(3)));

                    Student student = new Student();
                    student.setStudentID(Integer.parseInt(cursor.getString(4)));
                    student.setFirstName(cursor.getString(5));
                    student.setLastName(cursor.getString(6));

                    studentInCourse.setStudent(student);

                    Course newCourse = new Course();
                    newCourse.setCourseName(cursor.getString(7));

                    studentInCourse.setCourse(newCourse);

                    attendance.setStudentInCourse(studentInCourse);

                    attendances.add(attendance);
                } while (cursor.moveToNext());

            }
            cursor.close();
        }

        db.close();
        return attendances;
    }

    public ArrayList<Attendance> getAttendanceForStudentDate(Student student, String date) {
        String query = "Select attendance.id, attendance.present, attendance.classdate, studentincourse.id, "
                + " student.studentid, student.firstname, student.lastname, course.coursename, course.courseid FROM " + TABLE_ATTENDANCE
                + " LEFT JOIN studentincourse ON attendance.studentcourseid = studentincourse.id"
                + " JOIN student ON studentincourse.studentid = student.studentid"
                + " JOIN course ON studentincourse.courseid = course.courseid"
                + " WHERE studentincourse.studentid =  \"" + student.getStudentID() + "\""
                + " AND attendance.classdate =  \"" + date + "\"";

        ArrayList<Attendance> attendances = new ArrayList<>();

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(query, null);

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    Attendance attendance = new Attendance();
                    attendance.setId(Integer.parseInt(cursor.getString(0)));
                    attendance.setPresent(Integer.parseInt(cursor.getString(1)));
                    attendance.setDate(cursor.getString(2));

                    StudentInCourse studentInCourse = new StudentInCourse();
                    studentInCourse.setID(Integer.parseInt(cursor.getString(3)));

                    Student newStudent = new Student();
                    newStudent.setStudentID(Integer.parseInt(cursor.getString(4)));
                    newStudent.setFirstName(cursor.getString(5));
                    newStudent.setLastName(cursor.getString(6));

                    studentInCourse.setStudent(newStudent);

                    Course newCourse = new Course();
                    newCourse.setCourseID(Integer.parseInt(cursor.getString(8)));
                    newCourse.setCourseName(cursor.getString(7));

                    studentInCourse.setCourse(newCourse);

                    attendance.setStudentInCourse(studentInCourse);

                    attendances.add(attendance);
                } while (cursor.moveToNext());

            }
            cursor.close();
        }

        db.close();
        return attendances;
    }

    public ArrayList<Attendance> getAttendanceForCourseDateRange(Course course, String startDate, String endDate) {
        String query = "Select attendance.id, attendance.present, attendance.classdate, studentincourse.id, "
                + " student.studentid, student.firstname, student.lastname, Count(attendance.present) AS countDays, "
                + " SUM(attendance.present) AS countPresent, course.coursename FROM " + TABLE_ATTENDANCE
                + " LEFT JOIN studentincourse ON attendance.studentcourseid = studentincourse.id"
                + " JOIN student ON studentincourse.studentid = student.studentid"
                + " JOIN course ON studentincourse.courseid = course.courseid"
                + " WHERE studentincourse.courseid =  \"" + course.getCourseID() + "\""
                + " AND attendance.classdate BETWEEN  \"" + startDate + "\" AND \"" + endDate + "\""
                + " GROUP BY student.studentid";

        ArrayList<Attendance> attendances = new ArrayList<>();

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(query, null);

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    Attendance attendance = new Attendance();
                    attendance.setId(Integer.parseInt(cursor.getString(0)));
                    attendance.setPresent(Integer.parseInt(cursor.getString(1)));
                    attendance.setDate(cursor.getString(2));
                    attendance.setCountDays(Integer.parseInt(cursor.getString(7)));
                    attendance.setCountPresent(Integer.parseInt(cursor.getString(8)));

                    StudentInCourse studentInCourse = new StudentInCourse();
                    studentInCourse.setID(Integer.parseInt(cursor.getString(3)));

                    Student student = new Student();
                    student.setStudentID(Integer.parseInt(cursor.getString(4)));
                    student.setFirstName(cursor.getString(5));
                    student.setLastName(cursor.getString(6));

                    studentInCourse.setStudent(student);

                    Course newCourse = new Course();
                    newCourse.setCourseName(cursor.getString(9));

                    studentInCourse.setCourse(newCourse);

                    attendance.setStudentInCourse(studentInCourse);

                    attendances.add(attendance);
                } while (cursor.moveToNext());

            }
            cursor.close();
        }

        db.close();
        return attendances;
    }

    public ArrayList<Attendance> getAttendanceForStudentDateRange(Student student, String startDate, String endDate) {
        String query = "Select attendance.id, attendance.present, attendance.classdate, studentincourse.id, "
                + " student.studentid, student.firstname, student.lastname, Count(attendance.present) AS countDays, "
                + " SUM(attendance.present) AS countPresent, course.coursename, course.courseid FROM " + TABLE_ATTENDANCE
                + " LEFT JOIN studentincourse ON attendance.studentcourseid = studentincourse.id"
                + " JOIN student ON studentincourse.studentid = student.studentid"
                + " JOIN course ON studentincourse.courseid = course.courseid"
                + " WHERE studentincourse.studentid =  \"" + student.getStudentID() + "\""
                + " AND attendance.classdate BETWEEN  \"" + startDate + "\" AND \"" + endDate + "\""
                + " GROUP BY course.courseid";

        ArrayList<Attendance> attendances = new ArrayList<>();

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(query, null);

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    Attendance attendance = new Attendance();
                    attendance.setId(Integer.parseInt(cursor.getString(0)));
                    attendance.setPresent(Integer.parseInt(cursor.getString(1)));
                    attendance.setDate(cursor.getString(2));
                    attendance.setCountDays(Integer.parseInt(cursor.getString(7)));
                    attendance.setCountPresent(Integer.parseInt(cursor.getString(8)));

                    StudentInCourse studentInCourse = new StudentInCourse();
                    studentInCourse.setID(Integer.parseInt(cursor.getString(3)));

                    Student newStudent = new Student();
                    newStudent.setStudentID(Integer.parseInt(cursor.getString(4)));
                    newStudent.setFirstName(cursor.getString(5));
                    newStudent.setLastName(cursor.getString(6));

                    studentInCourse.setStudent(newStudent);

                    Course newCourse = new Course();
                    newCourse.setCourseID(Integer.parseInt(cursor.getString(10)));
                    newCourse.setCourseName(cursor.getString(9));

                    studentInCourse.setCourse(newCourse);

                    attendance.setStudentInCourse(studentInCourse);

                    attendances.add(attendance);
                } while (cursor.moveToNext());

            }
            cursor.close();
        }

        db.close();
        return attendances;
    }

    public int checkIfAttendanceExists(Course course) {
        String query = "Select COUNT(*) FROM " + TABLE_ATTENDANCE
                + " JOIN studentincourse ON attendance.studentcourseid = studentincourse.id "
                + " WHERE courseid =  \"" + course.getCourseID() + "\""
                + " AND " + COLUMN_DATE + " =  \"" + getDateTime() + "\"";

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(query, null);
        int count;
        if (cursor.moveToFirst()) {
            cursor.moveToFirst();
            count = Integer.parseInt(cursor.getString(0));
            cursor.close();
        } else {
            count = 0;
        }

        db.close();
        return count;
    }

    public int checkIfCourseHasStudents(Course course) {
        String query = "Select COUNT(*) FROM " + TABLE_STUDENTINCOURSE
                + " WHERE courseid =  \"" + course.getCourseID() + "\"";

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(query, null);
        int count;
        if (cursor.moveToFirst()) {
            cursor.moveToFirst();
            count = Integer.parseInt(cursor.getString(0));
            cursor.close();
        } else {
            count = 0;
        }

        db.close();
        return count;
    }

    public List<Student> getAllStudents() {
        List<Student> students = new ArrayList<>();

        String query = "Select * FROM " + TABLE_STUDENT ;

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(query, null);

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    Student student = new Student();
                    student.setStudentID(Integer.parseInt(cursor.getString(0)));
                    student.setFirstName(cursor.getString(1));
                    student.setLastName(cursor.getString(2));

                    students.add(student);
                } while (cursor.moveToNext());

            }
            cursor.close();
        }

        db.close();
        return students;
    }

    public List<Course> getAllCourses() {
        List<Course> courses = new ArrayList<>();

        String query = "Select * FROM " + TABLE_COURSE ;

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(query, null);

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    Course course = new Course();
                    course.setCourseID(Integer.parseInt(cursor.getString(0)));
                    course.setCourseName(cursor.getString(1));

                    courses.add(course);
                } while (cursor.moveToNext());

            }
            cursor.close();
        }

        db.close();
        return courses;
    }

    public List<StudentInCourse> getAllStudentsInCourse(int courseID) {
        List<StudentInCourse> students = new ArrayList<>();

        String query = "Select id, student.studentid, firstname, lastname, coursename "
            + "FROM student JOIN studentincourse ON student.studentid = studentincourse.studentid "
                + "JOIN course ON studentincourse.courseid = course.courseid "
            + "WHERE studentincourse.courseid" + " = " + courseID;

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(query, null);

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    StudentInCourse studentInCourse = new StudentInCourse();
                    studentInCourse.setID(Integer.parseInt(cursor.getString(0)));

                    Student student = new Student();
                    student.setStudentID(Integer.parseInt(cursor.getString(1)));
                    student.setFirstName(cursor.getString(2));
                    student.setLastName(cursor.getString(3));

                    studentInCourse.setStudent(student);

                    Course course = new Course();
                    course.setCourseName(cursor.getString(4));

                    studentInCourse.setCourse(course);

                    students.add(studentInCourse);
                } while (cursor.moveToNext());

            }
            cursor.close();
        }
        db.close();
        return students;
    }

    public void rebuildDB() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_STUDENT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_COURSE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_STUDENTINCOURSE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ATTENDANCE);
        onCreate(db);

        Student wayne = new Student(1, "Wayne", "Davidson");
        addStudent(wayne);
        Student jill = new Student(2, "Jill", "Smith");
        addStudent(jill);
        Student bob = new Student(3, "Bob", "Trainor");
        addStudent(bob);
        Student fred = new Student(4, "Fred", "Flintstone");
        addStudent(fred);
        Student angie = new Student(5, "Angie", "Zamora");
        addStudent(angie);

        Course cs101 = new Course(1, "CS101");
        addCourse(cs101);
        Course cs102 = new Course(2, "CS102");
        addCourse(cs102);
        Course cs103 = new Course(3, "CS103");
        addCourse(cs103);

        StudentInCourse waynecs101 = new StudentInCourse(1, wayne, cs101);
        addStudentInCourse(waynecs101);
        StudentInCourse jillcs101 = new StudentInCourse(2, jill, cs101);
        addStudentInCourse(jillcs101);
        StudentInCourse bobcs101 = new StudentInCourse(3, bob, cs101);
        addStudentInCourse(bobcs101);
        StudentInCourse fredcs101 = new StudentInCourse(4, fred, cs101);
        addStudentInCourse(fredcs101);
        StudentInCourse angiecs101 = new StudentInCourse(5, angie, cs101);
        addStudentInCourse(angiecs101);

        StudentInCourse waynecs102 = new StudentInCourse(6, wayne, cs102);
        addStudentInCourse(waynecs102);
        StudentInCourse fredcs102 = new StudentInCourse(7, fred, cs102);
        addStudentInCourse(fredcs102);

        Attendance cs101042016wayne = new Attendance(waynecs101, "2016-04-20", 1);
        addAttendance(cs101042016wayne);
        Attendance cs101042016jill = new Attendance(jillcs101, "2016-04-20", 1);
        addAttendance(cs101042016jill);
        Attendance cs101042016bob = new Attendance(bobcs101, "2016-04-20", 0);
        addAttendance(cs101042016bob);
        Attendance cs101042016fred = new Attendance(fredcs101, "2016-04-20", 1);
        addAttendance(cs101042016fred);
        Attendance cs101042016angie = new Attendance(angiecs101, "2016-04-20", 0);
        addAttendance(cs101042016angie);

        Attendance cs101042116wayne = new Attendance(waynecs101, "2016-04-21", 0);
        addAttendance(cs101042116wayne);
        Attendance cs101042116jill = new Attendance(jillcs101, "2016-04-21", 1);
        addAttendance(cs101042116jill);
        Attendance cs101042116bob = new Attendance(bobcs101, "2016-04-21", 0);
        addAttendance(cs101042116bob);
        Attendance cs101042116fred = new Attendance(fredcs101, "2016-04-21", 1);
        addAttendance(cs101042116fred);
        Attendance cs101042116angie = new Attendance(angiecs101, "2016-04-21", 1);
        addAttendance(cs101042116angie);

        Attendance cs101042216wayne = new Attendance(waynecs101, "2016-04-22", 0);
        addAttendance(cs101042216wayne);
        Attendance cs101042216jill = new Attendance(jillcs101, "2016-04-22", 1);
        addAttendance(cs101042216jill);
        Attendance cs101042216bob = new Attendance(bobcs101, "2016-04-22", 0);
        addAttendance(cs101042216bob);
        Attendance cs101042216fred = new Attendance(fredcs101, "2016-04-22", 1);
        addAttendance(cs101042216fred);
        Attendance cs101042216angie = new Attendance(angiecs101, "2016-04-22", 1);
        addAttendance(cs101042216angie);

        Attendance cs102042016wayne = new Attendance(waynecs102, "2016-04-20", 1);
        addAttendance(cs102042016wayne);
        Attendance cs102042016fred = new Attendance(fredcs102, "2016-04-20", 1);
        addAttendance(cs102042016fred);

        Attendance cs102042116wayne = new Attendance(waynecs102, "2016-04-21", 0);
        addAttendance(cs102042116wayne);
        Attendance cs102042116fred = new Attendance(fredcs102, "2016-04-21", 1);
        addAttendance(cs102042116fred);

        Attendance cs102042216wayne = new Attendance(waynecs102, "2016-04-22", 0);
        addAttendance(cs102042216wayne);
        Attendance cs102042216fred = new Attendance(fredcs102, "2016-04-22", 1);
        addAttendance(cs102042216fred);


    }

    private String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }
}
