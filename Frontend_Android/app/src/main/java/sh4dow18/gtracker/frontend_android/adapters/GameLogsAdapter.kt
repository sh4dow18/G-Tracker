package sh4dow18.gtracker.frontend_android.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import sh4dow18.gtracker.frontend_android.R
import sh4dow18.gtracker.frontend_android.databinding.GameCardItemBinding
import sh4dow18.gtracker.frontend_android.utils.GameLogResponse

class GameLogsAdapter : RecyclerView.Adapter<MainViewHolder>() {
    private var gamesLogsList = mutableListOf<GameLogResponse>()

    @SuppressLint("NotifyDataSetChanged")
    fun setGamesList(newGamesList: List<GameLogResponse>){
        this.gamesLogsList.clear()
        this.gamesLogsList.addAll(newGamesList.toMutableList())
        this.notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = GameCardItemBinding.inflate(inflater, parent, false)
        return MainViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return gamesLogsList.size
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        val gameLog = gamesLogsList[position]
        val game = gameLog.game
        if (game.imageUrl != "") {
            Glide.with(holder.itemView.context)
                .load(game.imageUrl)
                .centerCrop()
                .into(holder.binding.GameImage)
        }
        var finished = "No"
        if (gameLog.finished) {
            finished = "Yes"
        }
        var finishedAtAll = "No"
        if (gameLog.finishedAtAll) {
            finishedAtAll = "Yes"
        }
        val gameRating = "Rating(Users): ${game.rating} / 5"
        val gameFinished = "Finished: $finished"
        val gameFinishedAtAll = "100%: $finishedAtAll"
        val gameTracker = "$gameFinished / $gameFinishedAtAll"
        holder.binding.GameName.text = game.name
        holder.binding.Metacritic.text = gameRating
        holder.binding.Genders.text = gameTracker
        holder.binding.GameItemContainer.setOnClickListener {
            val bundle = bundleOf("game_log_id" to gamesLogsList[position].id)
            holder.itemView.findNavController().navigate(R.id.nav_game_log, bundle)
        }
    }
}