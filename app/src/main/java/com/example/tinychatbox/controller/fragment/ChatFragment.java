package com.example.tinychatbox.controller.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.core.content.ContextCompat;

import com.example.tinychatbox.controller.activity.ChatActivity;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.easeui.constants.EaseConstant;
import com.hyphenate.easeui.modules.conversation.EaseConversationListFragment;
import com.hyphenate.easeui.modules.conversation.model.EaseConversationInfo;
import com.hyphenate.easeui.utils.EaseCommonUtils;

import static com.hyphenate.easeui.widget.EaseImageView.ShapeType.RECTANGLE;
//会话列表页面
public class ChatFragment extends EaseConversationListFragment {

    @Override
    public void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);


        //设置默认头像
        conversationListLayout.setAvatarDefaultSrc(ContextCompat.getDrawable(mContext, com.hyphenate.easeui.R.drawable.em_chat_voice_call_normal));
        conversationListLayout.setAvatarShapeType(RECTANGLE);
        conversationListLayout.setAvatarRadius((int) EaseCommonUtils.dip2px(mContext, 5));
    }


    //设置条目的点击事件
    @Override
    public void onItemClick(View view, int position) {

        super.onItemClick(view, position);
        Intent intent = new Intent(getActivity(), ChatActivity.class);
        //获取到该条目的所有数据
        EaseConversationInfo conversationInfo = conversationListLayout.getItem(position);
        //拿到该条目的会话信息
        EMConversation conversation = (EMConversation) conversationInfo.getInfo();
        //传递参数 会话信息id=hxid
        intent.putExtra(EaseConstant.EXTRA_CONVERSATION_ID, conversation.conversationId());

        if (conversationInfo.isGroup()) {
            intent.putExtra(EaseConstant.EXTRA_CHAT_TYPE, EaseConstant.CHATTYPE_GROUP);
        }
        startActivity(intent);
    }
}


