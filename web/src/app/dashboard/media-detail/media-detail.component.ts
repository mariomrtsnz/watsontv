import { DialogSeasonComponent } from './../../dialogs/dialog-season/dialog-season.component';
import { DialogDeleteSeasonComponent } from './../../dialogs/dialog-delete-season/dialog-delete-season.component';
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
import { OneSeasonResponse } from 'src/app/interfaces/one-season-response';
import { SeasonService } from 'src/app/services/season.service';

@Component({
  selector: 'app-media-detail',
  templateUrl: './media-detail.component.html',
  styleUrls: ['./media-detail.component.scss']
})
export class MediaDetailComponent implements OnInit {

  allGenres: OneGenreResponse[];
  media: OneMediaResponse;
  coverImage: string;
  mediaSeasons: OneSeasonResponse[];

  constructor(private mediaService: MediaService, private genreService: GenreService,
    // tslint:disable-next-line:max-line-length
    public router: Router, public snackBar: MatSnackBar, private titleService: Title, public dialog: MatDialog, private seasonService: SeasonService) { }

  ngOnInit() {
    if (!this.mediaService.selectedMedia) {
      this.router.navigate(['/home']);
    }
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

  openSeasonDetails(s: OneSeasonResponse) {
    this.seasonService.selectedSeason = s;
    this.router.navigate(['/home/season/episodes']);
  }

  openDialogCreateSeason(s: OneSeasonResponse) {
    const createSeasonDialog = this.dialog.open(DialogSeasonComponent, { panelClass: 'add-dialog' });

    createSeasonDialog.afterClosed().subscribe(result => {
      if (result === 'confirm') {
        this.getData();
      }
    });
  }

  openEditSeason(s: OneSeasonResponse) {
    const updateSeasonDialog = this.dialog.open(DialogSeasonComponent, { panelClass: 'add-dialog', data: { season: s } });

    updateSeasonDialog.afterClosed().subscribe(result => {
      if (result === 'confirm') {
        this.getData();
      }
    });
  }

  openDialogDeleteSeason(s: OneSeasonResponse) {
    const deleteSeasonDialog = this.dialog.open(DialogDeleteSeasonComponent,
      { panelClass: 'delete-dialog', data: { seasonId: s.id, seasonNumber: s.number } });

      deleteSeasonDialog.afterClosed().subscribe(result => {
      if (result === 'confirm') {
        this.getData();
      }
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
