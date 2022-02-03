import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 * For this to work, you must first create a database: https://firebase.google.com/
 * <p>
 * And use the "service accounts" menu to generate a JSON key for access.
 * <p>
 * Check here for more code samples: https://firebase.google.com/docs/database/admin/save-data
 */
public class FirebaseDemo extends JPanel implements ActionListener, ChildEventListener {

  private final ArrayList<Post> posts;

  private final JTextField nameField;
	private final JTextField messageField;
  private final JTextArea output;

  private DatabaseReference postsRef;

  public FirebaseDemo() {
    super(new BorderLayout());

    // UI SETUP
    posts = new ArrayList<Post>();
    output = new JTextArea();
    JScrollPane scrollPane = new JScrollPane(output);
    add(scrollPane, BorderLayout.CENTER);
    nameField = new JTextField(20);
    messageField = new JTextField(20);

    JButton goButton = new JButton("POST");
    goButton.addActionListener(this);

    JPanel bottomPanel = new JPanel();
    bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.Y_AXIS));

    JPanel namePanel = new JPanel();
    namePanel.add(new JLabel("Name: "));
    namePanel.add(nameField);

    JPanel messagePanel = new JPanel();
    messagePanel.add(new JLabel("Message: "));
    messagePanel.add(messageField);

    bottomPanel.add(namePanel);
    bottomPanel.add(messagePanel);
    bottomPanel.add(goButton);
    JPanel holder = new JPanel();
    holder.add(bottomPanel);
    add(holder, BorderLayout.SOUTH);

    // DATABASE SETUP
    FileInputStream refreshToken;
    try {

      refreshToken = new FileInputStream("--add json file here--");

      FirebaseOptions options = new FirebaseOptions.Builder()
          .setCredentials(GoogleCredentials.fromStream(refreshToken))
          .setDatabaseUrl("--inset database URL here--")
          .build();

      FirebaseApp.initializeApp(options);
      DatabaseReference database = FirebaseDatabase.getInstance().getReference();
      postsRef = database.child("post");

      postsRef.addChildEventListener(this);

    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

  }

  public static void main(String[] args) throws IOException {

    JFrame w = new JFrame("FirebaseDemo");
    w.setBounds(100, 100, 800, 600);
    w.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    FirebaseDemo panel = new FirebaseDemo();

    w.add(panel);
    w.setResizable(true);
    w.setVisible(true);
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    postsRef.push().setValueAsync(new Post(messageField.getText(), nameField.getText()));

  }

  @Override
  public void onCancelled(DatabaseError arg0) {
    // TODO Auto-generated method stub
  }


  @Override
  public void onChildAdded(DataSnapshot dataSnapshot, String arg1) {
    Post post = dataSnapshot.getValue(Post.class);
    posts.add(post);

    String text = "";
		for (Post p : posts) {
			text += p + "\n";
		}

    output.setText(text);
  }


  @Override
  public void onChildChanged(DataSnapshot arg0, String arg1) {
    // TODO Auto-generated method stub
  }


  @Override
  public void onChildMoved(DataSnapshot arg0, String arg1) {
    // TODO Auto-generated method stub
  }


  @Override
  public void onChildRemoved(DataSnapshot arg0) {
    // TODO Auto-generated method stub
  }

}
