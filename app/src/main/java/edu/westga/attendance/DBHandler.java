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

    public Student findStudent(String firstName, String lastName) {
        String query = "Select * FROM " + TABLE_STUDENT + " WHERE " + COLUMN_FIRSTNAME + " =  \"" + firstName + "\""
                + " AND " + COLUMN_LASTNAME + " =  \"" + lastName + "\"";

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(query, null);

        Student student = new Student();

        if (cursor.moveToFirst()) {
            cursor.moveToFirst();
            student.setStudentID(Integer.parseInt(cursor.getString(0)));
            student.setFirstName(cursor.getString(1));
            student.setLastName(cursor.getString(2));
            cursor.close();
        } else {
            student = null;
        }
        db.close();
        return student;
    }

    public int checkIfStudentExists(String firstName, String lastName) {
        String query = "Select COUNT(*) FROM " + TABLE_STUDENT + " WHERE " + COLUMN_FIRSTNAME + " =  \"" + firstName + "\""
                + " AND " + COLUMN_LASTNAME + " =  \"" + lastName + "\"";

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

    public Course findCourse(String courseName) {
        String query = "Select * FROM " + TABLE_COURSE + " WHERE " + COLUMN_COURSENAME + " =  \"" + courseName + "\"";

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(query, null);

        Course course = new Course();

        if (cursor.moveToFirst()) {
            cursor.moveToFirst();
            course.setCourseID(Integer.parseInt(cursor.getString(0)));
            course.setCourseName(cursor.getString(1));
            cursor.close();
        } else {
            course = null;
        }
        db.close();
        return course;
    }

    public int checkIfCourseExists(String courseName) {
        String query = "Select COUNT(*) FROM " + TABLE_COURSE + " WHERE " + COLUMN_COURSENAME + " =  \"" + courseName + "\"";

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

        String query = "Select id, student.studentid, firstname, lastname "
            + "FROM student JOIN studentincourse ON student.studentid = studentincourse.studentid "
            + "WHERE courseid" + " = " + courseID;

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

                    students.add(studentInCourse);
                } while (cursor.moveToNext());

            }
            cursor.close();
        }
        db.close();
        return students;
    }

    public ArrayList<Attendance> getAllAttendance() {
        ArrayList<Attendance> attendances = new ArrayList<>();

        String query = "Select * FROM " + TABLE_ATTENDANCE ;

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(query, null);

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    Attendance attendance = new Attendance();
                    attendance.setId(Integer.parseInt(cursor.getString(0)));
                    //attendance.setStudentInCourse(Integer.parseInt(cursor.getString(1)));
                    attendance.setDate(cursor.getString(2));
                    attendance.setPresent(Integer.parseInt(cursor.getString(3)));

                    attendances.add(attendance);
                } while (cursor.moveToNext());

            }
            cursor.close();
        }

        db.close();
        return attendances;
    }

    public void rebuildDB() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_STUDENT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_COURSE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_STUDENTINCOURSE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ATTENDANCE);
        onCreate(db);
    }

    private String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }
}
