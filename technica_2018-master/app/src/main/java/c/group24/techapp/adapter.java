package c.group24.techapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.List;

public class adapter extends BaseExpandableListAdapter {

    private Context context;
    private List<String> headerItem;
    private HashMap<String, List<String>> childItem;


    public adapter(Context context, List<String> headerItem, HashMap<String, List<String>> childItem) {
        this.context = context;
        this.headerItem = headerItem;
        this.childItem = childItem;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return childItem.get(headerItem.get(groupPosition)).get(childPosition);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(final int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        String childText = (String) getChild(groupPosition, childPosition);
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            if(!isLastChild) {
                convertView = inflater.inflate(R.layout.list_item, null);
            }else{
                convertView = inflater.inflate(R.layout.list_item_with_button, null);
                Button join = convertView.findViewById(R.id.myButton);
                join.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        FirebaseAuth auth = FirebaseAuth.getInstance();
                        String nameStr = auth.getCurrentUser().getUid();
                        String proj_name = headerItem.get(groupPosition);
                        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Taken projects and members").child(proj_name);
                        ref.child(nameStr).setValue(auth.getCurrentUser().getEmail());


                    }
                });
            }
        }
        TextView tv = convertView.findViewById(R.id.lblListItem);
        tv.setText(childText);

        return convertView;

    }


    @Override
    public int getChildrenCount(int groupPosition) {
        return childItem.get(headerItem.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return headerItem.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return headerItem.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

        String headerTitle = (String) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_group, null);
        }
        TextView tv = convertView.findViewById(R.id.lblListHeader);
        tv.setText(headerTitle);
        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}