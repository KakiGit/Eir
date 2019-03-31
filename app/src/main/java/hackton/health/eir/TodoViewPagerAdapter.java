package hackton.health.eir;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by wanghongkuan on 2019/3/30.
 */

public class TodoViewPagerAdapter extends RecyclerView.Adapter<TodoViewPagerAdapter.ViewHolder>{

    private Context mContext;
    private static String[] mNames = {"Event:","Date:","Things to bring with:","Address:"};
    private static String[] mValues = {"First contact with Neuvola", "08/05/2019", "Pregnancy certificate, Photo ID, Test report", "Haaga Counselor, Huovitie 5, 00400 Helsinki"};

    @Override
    public int getItemCount() {
        return mNames.length;
    }

    public TodoViewPagerAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public TodoViewPagerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.upcoming_events, parent, false);

        return new TodoViewPagerAdapter.ViewHolder(view) {
        };
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        ((TextView)holder.getCardView().findViewById(R.id.name)).setText(mNames[position]);
        ((TextView)holder.getCardView().findViewById(R.id.value)).setText(mValues[position]);

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                Intent intent = new Intent(mContext, GalleryActivity.class);
//                intent.putExtra("image_url", mImages.get(position));
//                intent.putExtra("image_name", mImageNames.get(position));
//                mContext.startActivity(intent);
            }
        });

    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private CardView cardView;
        public ViewHolder(View view) {
            super(view);
//            event = view.findViewById(R.id.upcoming_event);
            cardView = view.findViewById(R.id.upcomming);
        }

        public CardView getCardView() {
            return cardView;
        }
    }
}
