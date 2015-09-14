package com.example.samson.smpr_lab_1.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.samson.smpr_lab_1.MainActivity;
import com.example.samson.smpr_lab_1.MatrixAdapter;
import com.example.samson.smpr_lab_1.R;
import com.example.samson.smpr_lab_1.custom.GraphView;
import com.example.samson.smpr_lab_1.utils.Property;

import java.util.ArrayList;

public class MainFragment extends Fragment implements View.OnClickListener, AdapterView.OnItemClickListener {

    private MainActivity activity;
    private GridView mMatrix;
    private ImageButton mMore, mLess, mRefresh;
    private Button mCheck, mShowGraph, mShowMatrix;
    private TextView mCount, tvRmax, tvRmin;
    private CheckBox cbRef, cbIrref, cbSym, cbAsym, cbAntiSym, cbTran, cbTotal, cbRmax, cbRmin;
    private CheckBox[] checkGroup;
    private MatrixAdapter adapter;
    private RelativeLayout rlMatrix, rlGraph;
    private GraphView graph;

    private ArrayList<Integer> vectorMatrix;
    private int sizeMatrix = 0;
    private static final int NUM_PROP = 9;
    private int[] properties = new int[NUM_PROP];
    private int[][] matrix;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = (MainActivity) activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        findUI(view);
        setListeners();
        setAdapter();
        return view;
    }

    private void findUI(View view){
        mMatrix = (GridView) view.findViewById(R.id.matrix);
        mMore = (ImageButton) view.findViewById(R.id.ibMore);
        mLess = (ImageButton) view.findViewById(R.id.ibLess);
        mCheck = (Button) view.findViewById(R.id.btnCheck);
        mShowGraph = (Button) view.findViewById(R.id.btnShowGraph);
        mShowMatrix = (Button) view.findViewById(R.id.btnShowMatrix);
        mRefresh = (ImageButton) view.findViewById(R.id.btnRefresh);
        mCount = (TextView) view.findViewById(R.id.tvCount);
        tvRmax = (TextView) view.findViewById(R.id.tvRMax);
        tvRmin = (TextView) view.findViewById(R.id.tvRMin);
        rlMatrix = (RelativeLayout) view.findViewById(R.id.rlMatrix);
        rlGraph = (RelativeLayout) view.findViewById(R.id.rlGraph);
        graph = (GraphView) view.findViewById(R.id.graphView);
        cbRef = (CheckBox) view.findViewById(R.id.cbReflexive);
        cbIrref = (CheckBox) view.findViewById(R.id.cbIrreflexive);
        cbSym = (CheckBox) view.findViewById(R.id.cbSymmetry);
        cbAsym = (CheckBox) view.findViewById(R.id.cbAsymmetry);
        cbAntiSym = (CheckBox) view.findViewById(R.id.cbAntisymmetry);
        cbTran = (CheckBox) view.findViewById(R.id.cbTransitive);
        cbTotal = (CheckBox) view.findViewById(R.id.cbTotal);
        cbRmax = (CheckBox) view.findViewById(R.id.cbRMax);
        cbRmin = (CheckBox) view.findViewById(R.id.cbRMin);
        initCheckGroup();
    }

    private void initCheckGroup(){
        checkGroup = new CheckBox[]{cbRef, cbIrref, cbSym, cbAsym, cbAntiSym, cbTran, cbTotal, cbRmax, cbRmin};
    }

    private void setListeners(){
        mMore.setOnClickListener(this);
        mLess.setOnClickListener(this);
        mCheck.setOnClickListener(this);
        mShowGraph.setOnClickListener(this);
        mShowMatrix.setOnClickListener(this);
        mRefresh.setOnClickListener(this);
        cbRef.setOnClickListener(this);
        cbIrref.setOnClickListener(this);
        cbSym.setOnClickListener(this);
        cbAsym.setOnClickListener(this);
        cbAntiSym.setOnClickListener(this);
        cbTran.setOnClickListener(this);
        cbTotal.setOnClickListener(this);
        cbRmax.setOnClickListener(this);
        cbRmin.setOnClickListener(this);
        mMatrix.setOnItemClickListener(this);
    }

    private void setAdapter(){
        vectorMatrix = new ArrayList<>();
        adapter = new MatrixAdapter(activity, vectorMatrix);
        mMatrix.setAdapter(adapter);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        vectorMatrix.set(position, vectorMatrix.get(position) == 0 ? 1 : 0);
        adapter.updateMatrix(vectorMatrix);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ibMore:
                incSize();
                break;
            case R.id.ibLess:
                decSize();
                break;
            case R.id.btnCheck:
                getResult();
                break;
            case R.id.btnShowGraph:
                showGraph();
                break;
            case R.id.btnShowMatrix:
                showMatrix();
                break;
            case R.id.btnRefresh:
                refresh();
                break;
            case R.id.cbReflexive:
                setCheckProperty(0);
                break;
            case R.id.cbIrreflexive:
                setCheckProperty(1);
                break;
            case R.id.cbSymmetry:
                setCheckProperty(2);
                break;
            case R.id.cbAsymmetry:
                setCheckProperty(3);
                break;
            case R.id.cbAntisymmetry:
                setCheckProperty(4);
                break;
            case R.id.cbTransitive:
                setCheckProperty(5);
                break;
            case R.id.cbTotal:
                setCheckProperty(6);
                break;
            case R.id.cbRMax:
                setCheckProperty(7);
                break;
            case R.id.cbRMin:
                setCheckProperty(8);
                break;
        }
    }

    private void setCheckProperty(int index){
        properties[index] = checkGroup[index].isChecked() ? 1 : 0;
    }

    private void incSize(){
        ++sizeMatrix;
        updateMatrix();
    }

    private void decSize() {
        if(--sizeMatrix < 0)
            sizeMatrix = 0;
        updateMatrix();
    }

    private void updateMatrix(){
        mCount.setText(String.valueOf(sizeMatrix));

        mMatrix.setNumColumns(sizeMatrix);
        vectorMatrix = new ArrayList<>();
        int n = sizeMatrix * sizeMatrix;
        for(int i = 0; i < n; ++i){
            vectorMatrix.add(0);
        }
        adapter.updateMatrix(vectorMatrix);
    }

    private void showGraph(){
        rlMatrix.setVisibility(View.GONE);
        rlGraph.setVisibility(View.VISIBLE);
        graph.setGraph(matrix, sizeMatrix);

        mShowGraph.setVisibility(View.GONE);
        mShowMatrix.setVisibility(View.VISIBLE);
    }

    private void showMatrix(){
        rlMatrix.setVisibility(View.VISIBLE);
        rlGraph.setVisibility(View.GONE);

        mShowMatrix.setVisibility(View.GONE);
        mShowGraph.setVisibility(View.VISIBLE);
    }

    private void refresh(){
        rlMatrix.setVisibility(View.VISIBLE);
        rlGraph.setVisibility(View.GONE);
        mShowMatrix.setVisibility(View.GONE);
        mShowGraph.setVisibility(View.GONE);

        sizeMatrix = 0;
        updateMatrix();

        for(int i = 0; i < NUM_PROP; ++i){
            checkGroup[i].setChecked(false);
            properties[i] = 0;
            showResult();
        }

        clearEnumR();
    }

    private void clearEnumR(){
        tvRmin.setText("");
        tvRmax.setText("");
    }

    private void getResult(){
        if(sizeMatrix != 0) {
            clearEnumR();
            transformMatrix();
            checkProperty();
            showResult();
            mShowGraph.setVisibility(mShowMatrix.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
        } else {
            Toast.makeText(activity, "Please, create matrix", Toast.LENGTH_SHORT).show();
        }
    }

    private void transformMatrix(){
        matrix = new int[sizeMatrix][sizeMatrix];
        for(int row = 0; row < sizeMatrix; ++row){
            for(int col = 0; col < sizeMatrix; ++col){
                matrix[row][col] = vectorMatrix.get(row * sizeMatrix + col);
            }
        }
    }

    private void checkProperty(){
        for(int i = 0; i < NUM_PROP; ++i){
            if(Math.abs(properties[i]) == 1){
                int state = 0;
                switch (i){
                    case 0:
                        state = Property.reflexive(matrix, sizeMatrix) ? 1 : -1;
                        break;
                    case 1:
                        state = Property.irreflexive(matrix, sizeMatrix) ? 1 : -1;
                        break;
                    case 2:
                        state = Property.symmetry(matrix, sizeMatrix) ? 1 : -1;
                        break;
                    case 3:
                        state = Property.asymmetry(matrix, sizeMatrix) ? 1 : -1;
                        break;
                    case 4:
                        state = Property.antisymmetry(matrix, sizeMatrix) ? 1 : -1;
                        break;
                    case 5:
                        state = Property.transitive(matrix, sizeMatrix) ? 1 : -1;
                        break;
                    case 6:
                        state = Property.total(matrix, sizeMatrix) ? 1 : -1;
                        break;
                    case 7:
                        ArrayList<Integer> Rmax = Property.sendRmax(matrix, sizeMatrix);
                        state = Rmax.size() == 0 ? -1 : 1;
                        printEnumR(Rmax, true);
                        break;
                    case 8:
                        ArrayList<Integer> Rmin = Property.sendRmin(matrix, sizeMatrix);
                        state = Rmin.size() == 0 ? -1 : 1;
                        printEnumR(Rmin, false);
                        break;
                }
                properties[i] = state;
            }
        }
    }

    private void printEnumR(ArrayList<Integer> list, boolean isMax){
        String message = "= {";
        for(int item : list){
            message += " " + item + " ";
        }
        message += "}";
        if(isMax)
            tvRmax.setText(message);
        else
            tvRmin.setText(message);
    }

    private void showResult(){
        for(int i = 0; i < NUM_PROP; ++i){
            switch (properties[i]){
                case 1:
                    checkGroup[i].setBackgroundColor(activity.getResources().getColor(R.color.bg_correct));
                    break;
                case -1:
                    checkGroup[i].setBackgroundColor(activity.getResources().getColor(R.color.bg_incorrect));
                    break;
                case 0:
                    checkGroup[i].setBackgroundColor(activity.getResources().getColor(R.color.bg_property));
                    break;
            }
        }
    }
}
