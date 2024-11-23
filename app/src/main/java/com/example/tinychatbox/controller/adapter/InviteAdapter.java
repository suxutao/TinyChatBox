package com.example.tinychatbox.controller.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.tinychatbox.R;
import com.example.tinychatbox.model.bean.InvationInfo;
import com.example.tinychatbox.model.bean.UserInfo;

import java.util.ArrayList;
import java.util.List;

public class InviteAdapter extends BaseAdapter {

    private Context mContext;
    private List<InvationInfo> mInvitationInfos=new ArrayList<>();
    private OnInviteListener mOnInviteListener;
    private InvationInfo invationInfo;

    public void refresh(List<InvationInfo>invationInfos){
        if (invationInfos != null) {
            mInvitationInfos.clear();
            mInvitationInfos.addAll(invationInfos);
            notifyDataSetChanged();
        }
    }

    public InviteAdapter(Context context,OnInviteListener onInviteListener) {
        mContext=context;
        mOnInviteListener=onInviteListener;
    }

    @Override
    public int getCount() {
        return mInvitationInfos==null?0:mInvitationInfos.size();
    }

    @Override
    public Object getItem(int position) {
        return mInvitationInfos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder=null;
        if (convertView==null){
            holder=new ViewHolder();

            convertView=View.inflate(mContext, R.layout.item_invite,null);

            holder.name=convertView.findViewById(R.id.tv_invite_name);
            holder.reason=convertView.findViewById(R.id.tv_invite_reason);
            holder.accept=convertView.findViewById(R.id.bt_invite_accept);
            holder.reject=convertView.findViewById(R.id.bt_invite_reject);

            convertView.setTag(holder);
        }else{
            holder=(ViewHolder) convertView.getTag();
        }

        invationInfo = mInvitationInfos.get(position);
        UserInfo user = invationInfo.getUser();

        if (user!=null){
            holder.name.setText(invationInfo.getUser().getName());
            holder.accept.setVisibility(View.GONE);
            holder.reject.setVisibility(View.GONE);

            if (invationInfo.getStatus()== InvationInfo.InvationStatus.NEW_INVITE){
                if (invationInfo.getReason()==null){
                    holder.reason.setText("添加好友");
                }else{
                    holder.reason.setText(invationInfo.getReason());
                }
                holder.accept.setVisibility(View.VISIBLE);
                holder.reject.setVisibility(View.VISIBLE);
            }else if (invationInfo.getStatus()== InvationInfo.InvationStatus.INVITE_ACCEPT){
                if (invationInfo.getReason()==null){
                    holder.reason.setText("接受邀请");
                }else{
                    holder.reason.setText(invationInfo.getReason());
                }
            }else if (invationInfo.getStatus()== InvationInfo.InvationStatus.INVITE_ACCEPT_BY_PEER){
                if (invationInfo.getReason()==null){
                    holder.reason.setText("邀请被接受");
                }else{
                    holder.reason.setText(invationInfo.getReason());
                }
            }

            holder.accept.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnInviteListener.onAccept(invationInfo);
                }
            });

            holder.reject.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnInviteListener.onReject(invationInfo);
                }
            });
        }



        return convertView;
    }

    private class ViewHolder{
        private TextView name;
        private TextView reason;

        private Button accept;
        private Button reject;
    }

    public interface OnInviteListener{
        void onAccept(InvationInfo invationInfo);

        void onReject(InvationInfo invationInfo);
    }
}
