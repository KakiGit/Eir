package hackton.health.eir;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class ToDoListAdapter extends RecyclerView.Adapter<ToDoListAdapter.ViewHolder> {

    private List<Object> contents;

    public ToDoListAdapter(List<Object> contents) {
        this.contents = contents;
    }

    @Override
    public ToDoListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.mcardview, parent, false);
        ;

        return new ToDoListAdapter.ViewHolder(view) {
        };
    }

    @Override
    public void onBindViewHolder(final ToDoListAdapter.ViewHolder holder, final int position) {
        final CardView cardView = holder.getCardView();
        holder.cvId = position;
//        final CardView.LayoutParams layoutParams = (CardView.LayoutParams)cardView.getLayoutParams();
        ((TextView)cardView.findViewById(R.id.weeknum)).setText(TestData.timeBefore[holder.cvId]);
        holder.cvId = position;
        if(TestData.healthCheck[holder.cvId]!=null){
            cardView.findViewById(R.id.healthCheck).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    TextView title = cardView.findViewById(R.id.title);
                    TextView description = cardView.findViewById(R.id.description);
                    TextView website = cardView.findViewById(R.id.website);

                    if(TestData.healthCheck[holder.cvId]!=null){
                        title.setVisibility(View.VISIBLE);
                        title.setText(TestData.healthCheck[holder.cvId]);
                        description.setVisibility(View.VISIBLE);
                        description.setText(TestData.healthCheckInfo[holder.cvId]);
                        website.setVisibility(View.VISIBLE);
                        website.setText(TestData.healthCheckWeb[holder.cvId]);
                        cardView.findViewById(R.id.imageView2).getLayoutParams().height = ViewGroup.LayoutParams.MATCH_PARENT;
                    } else {
                        title.setText("No recommendation for health check");
                        description.setText(null);
                        description.setVisibility(View.INVISIBLE);
                        website.setText(null);
                        website.setVisibility(View.INVISIBLE);
                    }

                }
            });
        } else
        {
            cardView.findViewById(R.id.healthCheck).setVisibility(View.INVISIBLE);
        }
        if(TestData.kelaBenefit[holder.cvId]!=null) {
            cardView.findViewById(R.id.benefit).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    TextView title = cardView.findViewById(R.id.title);
                    TextView description = cardView.findViewById(R.id.description);
                    TextView website = cardView.findViewById(R.id.website);
                    if(TestData.kelaBenefit[holder.cvId]!=null){
                        title.setVisibility(View.VISIBLE);
                        title.setText(TestData.kelaBenefit[holder.cvId]);
                        description.setVisibility(View.VISIBLE);
                        description.setText(TestData.kelaBenefitInfo[holder.cvId]);
                        website.setVisibility(View.VISIBLE);
                        website.setText(TestData.kelaBenefitWeb[holder.cvId]);
                        cardView.findViewById(R.id.imageView2).getLayoutParams().height = ViewGroup.LayoutParams.MATCH_PARENT;

                    } else {
                        title.setText("No recommendation for kela benefit");
                        description.setText(null);
                        description.setVisibility(View.INVISIBLE);
                        website.setText(null);
                        website.setVisibility(View.INVISIBLE);
                    }
                }
            });
        } else {
            cardView.findViewById(R.id.benefit).setVisibility(View.INVISIBLE);
        }


    }

    @Override
    public int getItemCount() {
        return contents.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private CardView cardView;
        public int cvId;
        public ViewHolder(final View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.card_view);
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    Log.d("Eir","???");
//                    Intent intent = new Intent(view.getContext(),ToDoList.class);
//                    intent.putExtra("gesId",gesId);
//                    view.getContext().startActivity(intent);
                }
            });
        }

        public CardView getCardView() {
            return cardView;
        }
    }
}
