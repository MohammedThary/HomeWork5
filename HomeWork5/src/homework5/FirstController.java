/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package homework5;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javax.persistence.*;

/**
 * FXML Controller class
 *
 * @author wwwmo
 */
public class FirstController implements Initializable {

    @FXML
    private BorderPane main_pane;
    @FXML
    private TextField id_tf;
    @FXML
    private TextField name_tf;
    @FXML
    private TextField grade_tf;
    @FXML
    private TextField major_tf;
    @FXML
    private TextField Cid_tf;
    @FXML
    private TextField Sid_tf;
    @FXML
    private TextField semester_tf;
    @FXML
    private Button add_to_Registration_btn;
    @FXML
    private Button show_btn;
    @FXML
    private Button add_btn;
    @FXML
    private Button update_btn;
    @FXML
    private Button delete_btn;
    @FXML
    private TextArea text_area;
    @FXML
    private TableView<Student> student_table;
    @FXML
    private TableColumn<Student, Integer> student_id_col;
    @FXML
    private TableColumn<Student, String> student_name_col;
    @FXML
    private TableColumn<Student, Integer> student_grade_col;
    @FXML
    private TableColumn<Student, String> student_major_col;
    @FXML
    private TableView<Registration> registration_table;
    @FXML
    private TableColumn<Registration, Integer> registration_sid_col;
    @FXML
    private TableColumn<Registration, Integer> registration_cid_col;
    @FXML
    private TableColumn<Registration, String> registration_semester_col;
    @FXML
    private TableView<Course> course_table;
    @FXML
    private TableColumn<Course, Integer> course_id_col;
    @FXML
    private TableColumn<Course, String> course_name_col;
    @FXML
    private TableColumn<Course, String> course_room_col;
    @FXML
    private Button SE_S_btn;
    @FXML
    private Button best_s_btn;
    @FXML
    private Button pass_btn;
    @FXML
    private Button less_grade;
    private EntityManagerFactory emf;
    private Student st;
    private Course co;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        this.emf = Persistence.createEntityManagerFactory("HomeWork5PU");
        try {
            this.show_course();
        } catch (Exception ex) {
            Logger.getLogger(FirstController.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.show_reg();
        student_table.getSelectionModel().selectedItemProperty().addListener(
         event-> show_selected_student());

    }

    @FXML
    private void btn_add_to_Registration_handel(ActionEvent event) {      
         Registration reg = new Registration();
        reg.setStudentId(( Sid_tf.getText()));
        st.setId(Integer.parseInt(Sid_tf.getText()));
        co.setId(Integer.parseInt(Cid_tf.getText()));
        reg.setSemseter(semester_tf.getText());
        reg.setCourseId(co);
        reg.setStudentId(st);
        
        if (reg!=null) {
            EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(reg);
        em.getTransaction().commit();
        em.close();
        this.clear_reg();
        this.show_reg();
        }
        
    }

    @FXML
    private void btn_show_handel(ActionEvent event) {
        this.show_stu();
    }

    @FXML
    private void btn_add_handel(ActionEvent event) {
        Student stu = new Student();
        stu.setId(Integer.parseInt(id_tf.getText()));
        stu.setId(Integer.parseInt(id_tf.getText()));
        stu.setName(name_tf.getText());
        stu.setMajor(major_tf.getText());
        if (stu!=null) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(stu);
        em.getTransaction().commit();
        em.close();
        this.clear();
        this.show_stu();
        
    }}

    @FXML
    private void btn_update_handel(ActionEvent event) { 
        EntityManager em = emf.createEntityManager();
        Student student = new Student();
        student.setId(Integer.parseInt(id_tf.getText()));
        student.setName(name_tf.getText());
        student.setMajor(major_tf.getText());
        student.setGrade(Integer.parseInt(grade_tf.getText()));
        if (em!=null) { 
        em.getTransaction().begin();
        em.merge(student);
        em.getTransaction().commit();
        em.close();
        clear();
        show_stu();   
    }}
    @FXML
    private void btn_less_S_handel(ActionEvent event) {
        EntityManager em = emf.createEntityManager();
        List<Student> students = em.createNamedQuery("Student.find_CS_stu").getResultList();
         int i = 0;
        while (i!=students.size()) {
            int e =students.get(i).getGrade();
            if (e<70) {
                students.get(i).setGrade(e+3);
            }
        }
        student_table.getItems().setAll(students);
         
        em.close();
    }
    @FXML
    private void btn_delete_handel(ActionEvent event) {
      EntityManager em = emf.createEntityManager();
        Student stu = em.find(Student.class, Integer.parseInt(id_tf.getText()));
        em.getTransaction().begin();
        em.remove(stu);
        em.getTransaction().commit();
        em.close();
        clear();
        show_stu(); 
    }
    

    @FXML
    private void btn_SE_S_handel(ActionEvent event) {
        EntityManager em = emf.createEntityManager();
        List<Student> students = em.createNamedQuery("Student.find_SE_stu").getResultList();
        student_table.getItems().setAll(students);
        em.close();
    }

    @FXML
    private void btn_best_S_handel(ActionEvent event) {
        EntityManager em = emf.createEntityManager();
        List<Student> students = em.createNamedQuery("Student.find_best").getResultList();
        student_table.getItems().setAll(students);
        em.close();
    }

    @FXML
    private void btn_pass_S_handel(ActionEvent event) {
        EntityManager em = emf.createEntityManager();
        List<Student> students = em.createNamedQuery("Student.findPass").getResultList();
        student_table.getItems().setAll(students);
        em.close();
    }



    private void show_course() throws Exception {
        student_id_col.setCellValueFactory(new PropertyValueFactory("id"));
        student_name_col.setCellValueFactory(new PropertyValueFactory("name"));
        student_major_col.setCellValueFactory(new PropertyValueFactory("major"));
        student_grade_col.setCellValueFactory(new PropertyValueFactory("grade"));
        registration_sid_col.setCellValueFactory(new PropertyValueFactory("studentId"));
        registration_cid_col.setCellValueFactory(new PropertyValueFactory("courseId"));
        registration_semester_col.setCellValueFactory(new PropertyValueFactory("semseter"));
        course_id_col.setCellValueFactory(new PropertyValueFactory("id"));
        course_name_col.setCellValueFactory(new PropertyValueFactory("name"));
        course_room_col.setCellValueFactory(new PropertyValueFactory("room"));
         



        EntityManager em = emf.createEntityManager();
        List<Course> courses = em.createNamedQuery("Course.findAll").getResultList();
        course_table.getItems().setAll(courses);
          em.close();
    }

    private void clear() {
        id_tf.setText("");
        name_tf.setText("");
        major_tf.setText("");
        grade_tf.setText("");
        student_table.getItems().clear();
    }

    private void clear_reg() {
        Sid_tf.setText("");
        Cid_tf.setText("");
        semester_tf.setText("");
        registration_table.getItems().clear();
    }
    private void show_stu(){
        clear();
    EntityManager em = emf.createEntityManager();
        List<Student> students = em.createNamedQuery("Student.findAll").getResultList();
        student_table.getItems().setAll(students);
        em.close();
    }
    
    private void show_reg(){
    EntityManager emr = emf.createEntityManager();
        List<Registration> registrations = emr.createNamedQuery("Registration.findAll").getResultList();      
        registration_table.getItems().setAll(registrations);
        emr.close();
    }
    
    private void show_selected_student (){
        Student stu = student_table.getSelectionModel().getSelectedItem();
         if (stu !=null) {
           id_tf.setText(String.valueOf(stu.getId()));
        name_tf.setText(stu.getName());
        major_tf.setText(stu.getMajor());
       grade_tf.setText(String.valueOf(stu.getGrade()));
       student_table.getItems().clear();  
         }
    }
}


/* List<Student> students = em.createNamedQuery("Student.findById")
                .setParameter("id", 12).getResultList();
        student_table.getItems().setAll(students); */
