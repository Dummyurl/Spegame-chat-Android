package surfaceholders;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import chat.spegame.com.speganet.R;
import model.Users;

/**
 * Created by toshiba on 05-Feb-18.
 */

public class Recycler_News extends RecyclerView.Adapter<Recycler_News.Newsdata> {
    Context context;
    List<Users> dataclass_newsList;

    public Recycler_News(Context context, List<Users> dataclass_newsList) {
        this.context = context;
        this.dataclass_newsList = dataclass_newsList;
    }

    @Override
    public Newsdata onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.contacts_cardview,parent,false);
        Newsdata newsdata=new Newsdata(view);
        return newsdata;
    }

    @Override
    public void onBindViewHolder(Newsdata holder, int position) {

        Users datas = dataclass_newsList.get(position);
        holder.head.setText(datas.getFullname());
        holder.play.setText(datas.getPhonenumber());

    }


    @Override
    public int getItemCount() {
        return dataclass_newsList.size();
    }

    public class Newsdata extends RecyclerView.ViewHolder {
        CardView cv;
        TextView head,play;
        ImageView img;
        public Newsdata(View itemView) {
            super(itemView);
            cv=(CardView)itemView.findViewById(R.id.news_cardview);
            head=(TextView)itemView.findViewById(R.id.textView_heading_news);
            play=(TextView)itemView.findViewById(R.id.textView_read);
            img=(ImageView)itemView.findViewById(R.id.imageView_news);
        }
    }
}
