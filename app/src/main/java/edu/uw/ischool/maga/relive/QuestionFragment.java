/*package edu.uw.ischool.maga.relive;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

public class QuestionFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_question, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        Question currentQuestion = MainApp.quiz[MainApp.current]; // Set current to be current question
        ListView nameSelect = (ListView) view.findViewById(R.id.select_name);

        ArrayAdapter<String> nameAdapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                android.R.id.text1,
                currentQuestion.nameOptions
        );
        nameAdapter.setOnItemClickListener(New AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(currentQuestion.nameOptions[i].equals(currentQuestion.correctName)){
                    // Go to answer fragment as correct
                } else {
                    // Go to answer fragment as incorrect
                }
            }
        });
        nameSelect.setAdapter(nameAdapter);
    }
}
*/