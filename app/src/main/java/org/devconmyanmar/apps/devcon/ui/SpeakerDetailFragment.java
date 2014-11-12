package org.devconmyanmar.apps.devcon.ui;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import com.bumptech.glide.Glide;
import com.readystatesoftware.systembartint.SystemBarTintManager;
import org.devconmyanmar.apps.devcon.R;
import org.devconmyanmar.apps.devcon.model.Speaker;
import org.devconmyanmar.apps.devcon.transformer.CircleTransformer;

import static org.devconmyanmar.apps.devcon.utils.LogUtils.makeLogTag;

public class SpeakerDetailFragment extends BaseFragment {
  private static final String ARG_TALK_ID = "param1";
  private static final String TAG = makeLogTag(SpeakerDetailFragment.class);

  @InjectView(R.id.speaker_detail_name) TextView mSpeakerName;
  @InjectView(R.id.speaker_detail_title) TextView mSpeakerTitle;
  @InjectView(R.id.speaker_detail_profile_image) ImageView mSpeakerProfileImage;
  @InjectView(R.id.speaker_detail_description) TextView mSpeakerDescription;

  private String mSpeakerId;
  public SpeakerDetailFragment() {
    // Required empty public constructor
  }

  public static SpeakerDetailFragment newInstance(String speakerId) {
    SpeakerDetailFragment fragment = new SpeakerDetailFragment();
    Bundle args = new Bundle();
    args.putString(ARG_TALK_ID, speakerId);
    fragment.setArguments(args);
    return fragment;
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    if (getArguments() != null) {
      mSpeakerId = getArguments().getString(ARG_TALK_ID);
    }
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    View rootView = inflater.inflate(R.layout.fragment_speaker_detail, container, false);

    ButterKnife.inject(this, rootView);
    if (Build.VERSION.SDK_INT >= 19) {

      SystemBarTintManager tintManager = new SystemBarTintManager(getActivity());
      tintManager.setStatusBarTintEnabled(true);
      tintManager.setNavigationBarTintEnabled(false);
      tintManager.setTintColor(getResources().getColor(R.color.translucent_actionbar_background));
    }
    Speaker speaker = speakerDao.getSpeakerById(mSpeakerId);

    if (speaker != null) {
      mSpeakerName.setText(speaker.getName());
      mSpeakerTitle.setText(speaker.getTitle());
      mSpeakerDescription.setText(speaker.getDescription());

      Glide.with(mContext)
          .load(speaker.getPhoto())
          .centerCrop()
          .error(R.drawable.person_image_empty)
          .placeholder(R.drawable.person_image_empty)
          .crossFade()
          .transform(new CircleTransformer(mContext))
          .into(mSpeakerProfileImage);
    }

    return rootView;
  }
}
