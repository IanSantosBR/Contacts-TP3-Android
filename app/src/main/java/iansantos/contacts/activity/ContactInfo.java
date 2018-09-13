package iansantos.contacts.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import iansantos.contacts.R;

public class ContactInfo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_info);
        setTitle("Informações de Contato");

        TextView name = findViewById(R.id.name);
        TextView phone = findViewById(R.id.phone);
        TextView email = findViewById(R.id.email);
        TextView city = findViewById(R.id.city);
        TextView password = findViewById(R.id.password);
        TextView cellPhone = findViewById(R.id.cellPhone);
        TextView cpf = findViewById(R.id.cpf);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        String nameText = (String) (bundle != null ? bundle.get("name") : null);
        String phoneText = (String) (bundle != null ? bundle.get("phone") : null);
        String emailText = (String) (bundle != null ? bundle.get("email") : null);
        String cityText = (String) (bundle != null ? bundle.get("city") : null);
        String passwordText = (String) (bundle != null ? bundle.get("password") : null);
        String cellPhoneText = (String) (bundle != null ? bundle.get("cellPhone") : null);
        String cpfText = (String) (bundle != null ? bundle.get("cpf") : null);

        name.setText(nameText);
        phone.setText(phoneText);
        email.setText(emailText);
        city.setText(cityText);
        password.setText(passwordText);
        cellPhone.setText(cellPhoneText);
        cpf.setText(cpfText);
    }
}
