import { DialogDeleteMediaComponent } from 'src/app/dialogs/dialog-delete-media/dialog-delete-media.component';
import { Title } from '@angular/platform-browser';
import { MatSnackBar, MatDialog } from '@angular/material';
import { Router } from '@angular/router';
import { GenreService } from './../../services/genre.service';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Component, OnInit } from '@angular/core';
import { MediaService } from 'src/app/services/media.service';
import { SeriesDto } from 'src/app/dto/series-dto';
import { MovieDto } from 'src/app/dto/movie-dto';
import { OneGenreResponse } from 'src/app/interfaces/one-genre-response';
import { OneMediaResponse } from 'src/app/interfaces/one-media-response';

@Component({
  selector: 'app-media-detail',
  templateUrl: './media-detail.component.html',
  styleUrls: ['./media-detail.component.scss']
})
export class MediaDetailComponent implements OnInit {

  allGenres: OneGenreResponse[];
  media: OneMediaResponse;
  coverImage: string;

  constructor(private mediaService: MediaService, private genreService: GenreService,
    public router: Router, public snackBar: MatSnackBar, private titleService: Title, public dialog: MatDialog) { }

  ngOnInit() {
    this.getData();
    this.getGenres();
    this.titleService.setTitle('Media - Detail');
  }

  getGenres() {
    this.genreService.getAll()
      .subscribe(genres => this.allGenres = genres.rows);
  }

  getData() {
      this.mediaService.getOne(this.mediaService.selectedMedia.id).subscribe(m => {
        this.media = m;
        this.coverImage = m.coverImage;
      });
  }

  openEditMedia() {
    this.router.navigate(['home/media/edit']);
  }

  openDialogDeleteMedia() {
    const deleteMediaDialog = this.dialog.open(DialogDeleteMediaComponent,
      { panelClass: 'delete-dialog', data: { mediaId: this.media.id, mediaTitle: this.media.title } });

      deleteMediaDialog.afterClosed().subscribe(result => {
      if (result === 'confirm') {
        this.router.navigate(['home']);
      }
    });
  }

}
