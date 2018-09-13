package iansantos.contacts.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import iansantos.contacts.R;
import iansantos.contacts.adapter.ContactAdapter;
import iansantos.contacts.model.Contact;
import iansantos.contacts.utils.RecyclerItemClickListener;

public class ContactList extends AppCompatActivity {

    public static List<Contact> contactList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_list);
        setTitle("Lista de Contatos");

        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        ContactAdapter adapter = new ContactAdapter(contactList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayout.VERTICAL));
        recyclerView.setAdapter(adapter);
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(), recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Contact contact = contactList.get(position);
                Intent intent = new Intent(ContactList.this, ContactInfo.class);
                intent.putExtra("name", contact.getName());
                intent.putExtra("phone", contact.getPhone());
                intent.putExtra("email", contact.getEmail());
                intent.putExtra("city", contact.getCity());
                intent.putExtra("password", contact.getPassword());
                intent.putExtra("cellPhone", contact.getCellPhone());
                intent.putExtra("cpf", contact.getCpf());
                startActivity(intent);
            }

            @Override
            public void onLongItemClick(View view, int position) {
            }

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            }
        }));
    }
}