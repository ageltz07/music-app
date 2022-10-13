package me.adamgeltz.musicapp.repository;

import me.adamgeltz.musicapp.model.Song;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SongRepository extends MongoRepository<Song, String> {

}
