package seven.more.queuesleuth;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import seven.more.queuesleuth.core.Department;

public class DepartmentAdapter extends ArrayAdapter<Department> {

    private Context context;
    private List<Department> items;

    public DepartmentAdapter(Context context, List<Department> departments) {
        super(context, android.R.layout.simple_list_item_1, departments);
        this.context = context;
        this.items = departments;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(android.R.layout.simple_list_item_1, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.findViewsIn(convertView);
        } else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.update(items.get(position));
        return convertView;
    }

    class ViewHolder{
        private TextView tv;

        public void update(Department department) {
            tv.setText(department.getName());
        }

        public void findViewsIn(View convertView) {
            tv = (TextView) convertView.findViewById(android.R.id.text1);
        }
    }
}
