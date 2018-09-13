package iansantos.contacts.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.annotation.Nullable;

import iansantos.contacts.R;
import iansantos.contacts.model.Contact;

public class MainActivity extends AppCompatActivity {
    private final String TAG = "MainActivity";
    private EditText name;
    private EditText phone;
    private EditText email;
    private EditText city;
    private EditText password;
    private EditText cellPhone;
    private EditText cpf;
    private ViewGroup viewGroup;
    private CollectionReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Contatos");
        viewGroup = findViewById(R.id.main_activity);
        name = findViewById(R.id.name_editText);
        phone = findViewById(R.id.phone_editText);
        email = findViewById(R.id.email_editText);
        city = findViewById(R.id.city_editText);
        password = findViewById(R.id.password_editText);
        cellPhone = findViewById(R.id.cellPhone_editText);
        cpf = findViewById(R.id.cpf_editText);
        FirebaseFirestore database = FirebaseFirestore.getInstance();
        databaseReference = database.collection("contacts");
    }

    public void onStart() {
        super.onStart();
        databaseReference.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    Log.w(TAG, "Listen error", e);
                }
            }
        });
    }

    public void showContacts(View view) {
        databaseReference.orderBy("name").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    List<Contact> contactList = new ArrayList<>();
                    for (DocumentSnapshot document : task.getResult()) {
                        Contact contact = document.toObject(Contact.class);
                        contactList.add(contact);
                    }
                    if (contactList.isEmpty()) {
                        Toast.makeText(MainActivity.this, "A lista de contatos está vazia", Toast.LENGTH_SHORT).show();
                    } else {
                        ContactList.contactList = contactList;
                        Intent intent = new Intent(MainActivity.this, ContactList.class);
                        startActivity(intent);
                    }
                } else {
                    Log.w(TAG, "Error getting documents", task.getException());
                }
            }
        });
    }

    public void saveContact(View view) {
        if (areValidFields()) {
            Contact contact = new Contact(name.getText().toString(), phone.getText().toString(), email.getText().toString(), city.getText().toString(), password.getText().toString(), cellPhone.getText().toString(), cpf.getText().toString());
            Map<String, Object> Contact = new HashMap<>();
            Contact.put("name", contact.getName());
            Contact.put("phone", contact.getPhone());
            Contact.put("email", contact.getEmail());
            Contact.put("city", contact.getCity());
            Contact.put("password", contact.getPassword());
            Contact.put("cellPhone", contact.getCellPhone());
            Contact.put("cpf", contact.getCpf());
            databaseReference.add(Contact).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                @Override
                public void onSuccess(DocumentReference documentReference) {
                    Log.i(TAG, "All changes were synchronized with the server");
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.w(TAG, "Error adding document", e);
                }
            });
            databaseReference.addSnapshotListener(new EventListener<QuerySnapshot>() {
                @Override
                public void onEvent(@Nullable QuerySnapshot querySnapshot, @Nullable FirebaseFirestoreException e) {
                    if (e != null) {
                        Log.w(TAG, "Listen error", e);
                        return;
                    }
                    if (querySnapshot != null) {
                        for (DocumentChange documentChange : querySnapshot.getDocumentChanges()) {
                            switch (documentChange.getType()) {
                                case ADDED:
                                    String source = querySnapshot.getMetadata().isFromCache() ? "cache local" : "servidor";
                                    Toast.makeText(MainActivity.this, "O contato foi adicionado no " + source, Toast.LENGTH_SHORT).show();
                                    break;
                                case MODIFIED:
                                    break;
                                case REMOVED:
                                    break;
                            }
                        }
                    }
                }
            });
            clear(view);
        } else {
            Toast.makeText(this, "Verifique os campos obrigatórios", Toast.LENGTH_SHORT).show();
        }
    }

    public boolean areValidFields() {
        boolean areValidFields = true;
        if (TextUtils.isEmpty(name.getText().toString().trim())) {
            name.setError("Digite o nome");
            areValidFields = false;
        }
        if (TextUtils.isEmpty(phone.getText().toString().trim())) {
            phone.setError("Digite o telefone");
            areValidFields = false;
        }
        if (TextUtils.isEmpty(email.getText().toString().trim())) {
            email.setError("Digite o email");
            areValidFields = false;
        }
        if (TextUtils.isEmpty(city.getText().toString().trim())) {
            city.setError("Digite a cidade");
            areValidFields = false;
        }
        if (TextUtils.isEmpty(password.getText().toString().trim())) {
            password.setError("Digite a senha");
            areValidFields = false;
        }
        if (TextUtils.isEmpty(cellPhone.getText().toString().trim())) {
            cellPhone.setError("Digite o celular");
            areValidFields = false;
        }
        if (TextUtils.isEmpty(cpf.getText().toString().trim())) {
            cpf.setError("Digite o CPF");
            areValidFields = false;
        }
        return areValidFields;
    }

    public void clear(View v) {
        ViewGroup group = findViewById(R.id.main_activity);
        for (int i = 0, count = group.getChildCount(); i < count; ++i) {
            View view = group.getChildAt(i);
            if (view instanceof TextInputLayout) {
                Objects.requireNonNull(((TextInputLayout) view).getEditText()).getText().clear();
                Objects.requireNonNull(((TextInputLayout) view).getEditText()).setError(null);
            }
        }
        viewGroup.requestFocus();
        hideKeyboard();
    }

    public void hideKeyboard() {
        InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        try {
            Objects.requireNonNull(inputManager).hideSoftInputFromWindow(Objects.requireNonNull(getCurrentFocus()).getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }
    }
}
