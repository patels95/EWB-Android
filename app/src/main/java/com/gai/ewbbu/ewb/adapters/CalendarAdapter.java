package com.gai.ewbbu.ewb.adapters;

import android.content.Context;
import android.content.Intent;
import android.provider.CalendarContract;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gai.ewbbu.ewb.R;
import com.gai.ewbbu.ewb.ui.CalendarDialog;
import com.gai.ewbbu.ewb.ui.CalendarSerializable;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventAttachment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class CalendarAdapter extends RecyclerView.Adapter<CalendarAdapter.CalendarViewHolder> {

    private static final String TAG = CalendarAdapter.class.getSimpleName();

    private Context mContext;
    private List<Event> mDataset = new ArrayList<>();

    public CalendarAdapter(Context context, List<Event> list){
        this.mContext = context;
        this.mDataset = list;
    }

    public class CalendarViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        CardView mCardView;
        TextView mEventTitle;
        TextView mEventLocation;
        TextView mEventTimeStart;
        TextView mEventTimeStartSubtext; // "Starts: "
        TextView mEventTimeEnd;
        TextView mEventTimeEndSubtext; // "Ends: "
        String mEventDescriptionString;
        List<EventAttachment> mEventAttachments = new ArrayList<>();

        public CalendarViewHolder(View v){
            super(v);

            mCardView = (CardView) v.findViewById(R.id.card_view);
            mEventTitle = (TextView) v.findViewById(R.id.textEventTitle);
            mEventLocation = (TextView) v.findViewById(R.id.textEventLocation);
            mEventTimeStart = (TextView) v.findViewById(R.id.textEventStartTime);
            mEventTimeStartSubtext = (TextView) v.findViewById(R.id.textEventStart);
            mEventTimeEnd = (TextView) v.findViewById(R.id.textEventEndTime);
            mEventTimeEndSubtext = (TextView) v.findViewById(R.id.textEventEnd);

            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int index = getLayoutPosition();
            Event event = mDataset.get(index);

            // create intent to add event to user's calendar
            Intent calIntent = new Intent(Intent.ACTION_EDIT);
            calIntent.setData(CalendarContract.Events.CONTENT_URI);

            // add event info to intent
            calIntent.putExtra(CalendarContract.Events.TITLE, event.getSummary());
            calIntent.putExtra(CalendarContract.Events.EVENT_LOCATION, event.getLocation());
            calIntent.putExtra(CalendarContract.Events.DESCRIPTION, event.getDescription());
            calIntent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, event.getStart().getDateTime().getValue());
            calIntent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME, event.getEnd().getDateTime().getValue());

            mContext.startActivity(calIntent);
        }
    }
    @Override
    public CalendarViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Set the new view here.
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cardview_calendarevent, parent, false);
        CalendarViewHolder cvh = new CalendarViewHolder(v);
        return cvh;
    }

    @Override
    public void onBindViewHolder(CalendarViewHolder holder, int position) {
        if (mDataset != null) {
            Event ev = mDataset.get(position);
            holder.mEventAttachments = ev.getAttachments();
            setupTitle(holder, ev);
            setupLocation(holder, ev);
            setupTimeStart(holder, ev);
            setupTimeEnd(holder, ev);
            setupDescription(holder, ev);
        }
    }

    private void setupDescription(CalendarViewHolder holder, Event ev) {
        holder.mEventDescriptionString = ev.getDescription();
    }

    private void setupTitle(CalendarViewHolder holder, Event ev) {
        String title = ev.getSummary().toString();
        title = title.replaceAll("\\[.*?\\]", ""); // Remove tags.
        holder.mEventTitle.setText(title);
        holder.mEventTitle.setSelected(true);
    }

    private void setupLocation(CalendarViewHolder holder, Event ev) {
        if (ev.getLocation() != null) {
            holder.mEventLocation.setText(ev.getLocation());
        } else {
            holder.mEventLocation.setVisibility(View.GONE);
        }
    }

    private void setupTimeStart(CalendarViewHolder holder, Event ev) {
        if (ev.getStart().getDateTime() != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd, h:mm a");
            DateTime dt = ev.getStart().getDateTime();
            Date date = new Date(dt.getValue());
            String formattedDateTime = sdf.format(date);
            holder.mEventTimeStart.setText(formattedDateTime);
        } else if (ev.getStart().getDate() != null){ // This will assume the event is all day.
            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd");
            com.google.api.client.util.DateTime dt = ev.getStart().getDate();
            Date date = new Date(dt.getValue());
            String formattedDate = sdf.format(date);
            holder.mEventTimeStart.setText(formattedDate + ", All Day");
        }
    }

    private void setupTimeEnd(CalendarViewHolder holder, Event ev) {
        if (ev.getEnd().getDateTime() != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd, h:mm a");
            DateTime dt = ev.getEnd().getDateTime();
            Date date = new Date(dt.getValue());
            String formattedDateTime = sdf.format(date);
            holder.mEventTimeEnd.setText(formattedDateTime);
        } else {
            holder.mEventTimeEnd.setVisibility(View.INVISIBLE);
            holder.mEventTimeEndSubtext.setVisibility(View.INVISIBLE);
        }
    }

    public void clearList(){
        if (mDataset != null) {
            mDataset.clear();
        }
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView){
        super.onAttachedToRecyclerView(recyclerView);
    }

    public void addItemToDataset(Event event){
        if (mDataset == null){
            mDataset = new ArrayList<>();
        }
        this.mDataset.add(event);
    }

    public boolean isDatasetNull(){
        if (mDataset == null){
            return true;
        }
        else return false;
    }

    @Override
    public int getItemCount() {
        if (mDataset == null){
            return 0; // Debugging. Should have at least one card view.
        }
        else {
            return mDataset.size();
        }
    }
}
