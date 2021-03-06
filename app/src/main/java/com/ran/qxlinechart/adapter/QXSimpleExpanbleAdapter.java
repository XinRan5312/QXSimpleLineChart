package com.ran.qxlinechart.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ran.qxlinechart.R;

import java.util.List;
import java.util.Map;

/**
 * Created by houqixin on 2017/6/12.
 */
public class QXSimpleExpanbleAdapter extends BaseExpandableListAdapter {

    // 4 Child types
    private static final int CHILD_TYPE_1 = 0;
    private static final int CHILD_TYPE_2 = 1;
    private static final int CHILD_TYPE_3 = 2;
    private static final int CHILD_TYPE_UNDEFINED = 3;
    // 3 Group types
    private static final int GROUP_TYPE_1 = 0;
    private static final int GROUP_TYPE_2 = 1;
    private static final int GROUP_TYPE_3 = 2;
    private Activity context;
    private Map<String, List<String>> comments_feed_collection;
    private List<String> group_list;

    public QXSimpleExpanbleAdapter(Activity context, List<String> group_list, Map<String, List<String>> comments_feed_collection) {
        this.context = context;
        this.comments_feed_collection = comments_feed_collection;
        this.group_list = group_list;
    }
    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return comments_feed_collection.get(group_list.get(groupPosition)).get(childPosition);
    }
    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        String groupName = group_list.get(groupPosition);
        List<String> groupContent = comments_feed_collection.get(groupName);
        return groupContent.size();
    }
    @Override
    public Object getGroup(int groupPosition) {
        return group_list.get(groupPosition);
    }
    @Override
    public int getGroupCount() {
        return group_list.size();
    }
    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        final String incoming_text = (String) getChild(groupPosition, childPosition);
        LayoutInflater inflater = context.getLayoutInflater();
        int childType = getChildType(groupPosition, childPosition);
        // We need to create a new "cell container"
        if (convertView == null ||  convertView.getTag() ==null||(Integer)convertView.getTag()!= childType) {
            switch (childType) {
                case CHILD_TYPE_1:
                    convertView = inflater.inflate(R.layout.home_new_user_loan, null);

                    break;
                case CHILD_TYPE_2:
                    convertView = inflater.inflate(R.layout.home_recommander_item, null);

                    break;
                case CHILD_TYPE_3:
                    convertView = inflater.inflate(R.layout.home_recommander_item, null);

                    break;
                case CHILD_TYPE_UNDEFINED:
                    convertView = inflater.inflate(R.layout.home_recommander_item,null);

                    break;
                default:
                    // Maybe we should implement a default behaviour but it should be ok we know there are 4 child types right?
                    break;
            }
        }
        // We'll reuse the existing one
        else {
            // There is nothing to do here really we just need to set the content of view which we do in both cases
        }
        switch (childType) {
            case CHILD_TYPE_1:
//                TextView description_child = (TextView) convertView.findViewById(R.id.description_of_ads_expandable_list_child_text_view);
//                description_child.setText(incoming_text);
                break;
            case CHILD_TYPE_2: //Define how to render the data on the CHILD_TYPE_2 layout
                break;
            case CHILD_TYPE_3: //Define how to render the data on the CHILD_TYPE_3 layout
                break;
            case CHILD_TYPE_UNDEFINED: //Define how to render the data on the CHILD_TYPE_UNDEFINED layout
                break;
        }
        convertView.setTag(childType);
        return convertView;
    }
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        final String incoming_text = (String) getGroup(groupPosition);
        int groupType = getGroupType(groupPosition);
        // We need to create a new "cell container"
        if (convertView == null || convertView.getTag() ==null||(Integer)convertView.getTag()!= groupType) {
            switch (groupType) {
                case GROUP_TYPE_1:
                    convertView = inflater.inflate(R.layout.group_one, null);

                    break;
                case GROUP_TYPE_2: // Am using the same layout cause am lasy and don't wanna
                    // create other ones but theses should be different // or the group type shouldnt exist
                    convertView = inflater.inflate(R.layout.group_two, null);
                    break;
                case GROUP_TYPE_3: // Am using the same layout cause am lasy and don't wanna create other ones but
                    // theses should be different // or the group type shouldnt exist
                    convertView = inflater.inflate(R.layout.group_three, null);
                    break;
                default: // Maybe we should implement a default behaviour but it should be ok we know there are
                    // 3 group types right?
                    break;
            }
        } // We'll reuse the existing one
        else {
            // There is nothing to do here really we just need to set the content of view which we do in both cases
        }
        switch (groupType) {
            case GROUP_TYPE_1:
//                TextView item = (TextView) convertView.findViewById(R.id.expandable_list_single_item_text_view_group);
//                item.setTypeface(null, Typeface.BOLD);
//                item.setText(incoming_text);
                break;
            case GROUP_TYPE_2: //TODO: Define how to render the data on the GROUPE_TYPE_2 layout
                // Since i use the same layout as GROUPE_TYPE_1 i could do the same thing as above
                // but i choose to do nothing
                break;
            case GROUP_TYPE_3: //TODO: Define how to render the data on the GROUPE_TYPE_3 layout
                // Since i use the same layout as GROUPE_TYPE_1 i could do the same thing as above but i
                // choose to do nothing
                break;
            default: // Maybe we should implement a default behaviour
                // but it should be ok we know there are 3 group types right?
                break;
        }
        convertView.setTag(groupType);
        return convertView;

    }
    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }


    @Override
    public int getChildTypeCount() {
        return 4; // I defined 4 child types (CHILD_TYPE_1, CHILD_TYPE_2, CHILD_TYPE_3, CHILD_TYPE_UNDEFINED)
    }

    @Override
    public int getGroupTypeCount() {
        return 3;
    }

    @Override
    public int getGroupType(int groupPosition) {
        switch (groupPosition) {
            case 0:
                return GROUP_TYPE_1;
            case 1:
                return GROUP_TYPE_2;
            case 2:
                return GROUP_TYPE_3;
            default:
                return GROUP_TYPE_2;
        }
    }

    @Override
    public int getChildType(int groupPosition, int childPosition) {
        switch (groupPosition) {
            case 0:
                switch (childPosition) {
                    case 0:
                        return CHILD_TYPE_1;
                    case 1:
                        return CHILD_TYPE_UNDEFINED;
                    case 2:
                        return CHILD_TYPE_UNDEFINED;
                }
                break;
            case 1:
                switch (childPosition) {
                    case 0:
                        return CHILD_TYPE_2;
                    case 1:
                        return CHILD_TYPE_3;
                    case 2:
                        return CHILD_TYPE_3;
                }
                break;
            default:
                return CHILD_TYPE_UNDEFINED;
        }
        return CHILD_TYPE_UNDEFINED;
    }
}