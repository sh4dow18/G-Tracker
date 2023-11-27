package sh4dow18.gtracker.frontend_android.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import sh4dow18.gtracker.frontend_android.databinding.GameCardItemBinding
import sh4dow18.gtracker.frontend_android.utils.GameDetails

class GamesAdapter : RecyclerView.Adapter<MainViewHolder>() {

    private var gamesList = mutableListOf<GameDetails>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        return MainViewHolder(GameCardItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int {
        return gamesList.size
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        val game = gamesList[position]
        Glide.with(holder.itemView.context)
            .load("http://192.168.0.13:8080/api/public/image/game/" +
                    game.id)
            .into(holder.binding.GameImage)
        val gameRating = "Rating(Metacritic)" + game.rating.toString()
        val gameGenders = "Genders: " + game.gendersList.toString()
        holder.binding.GameName.text = game.name
        holder.binding.Metacritic.text = gameRating
        holder.binding.Genders.text = gameGenders
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setClassroomAssetList(gamesList2: List<GameDetails>){
        this.gamesList.clear()
        this.gamesList.addAll(gamesList2.toMutableList())
        this.notifyDataSetChanged()
    }
}

class MainViewHolder(
    val binding : GameCardItemBinding
): RecyclerView.ViewHolder(binding.root)