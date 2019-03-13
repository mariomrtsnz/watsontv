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

  isCreate = false;
  isEdit = false;
  mediaType: string;
  form: FormGroup;
  allGenres: OneGenreResponse[];
  mediaId: string;
  media: OneMediaResponse;
  coverImage: string;

  constructor(private fb: FormBuilder, private mediaService: MediaService, private genreService: GenreService,
    public router: Router, public snackBar: MatSnackBar, private titleService: Title, public dialog: MatDialog) { }

  ngOnInit() {
    if (this.router.url.indexOf('/home/media/create') > -1) {
      this.isCreate = true;
      this.createCreateForm();
      this.titleService.setTitle('Create - Media');
    } else if (this.router.url.indexOf('/home/media/edit') > -1) {
      this.isEdit = true;
      this.createEditForm();
      this.titleService.setTitle('Edit - Media');
      this.getData();
    } else {
      this.getData();
    }
    this.getGenres();
  }

  getGenres() {
    this.genreService.getAll()
      .subscribe(genres => this.allGenres = genres.rows);
  }

  getData() {
      this.mediaService.getOne(this.mediaService.selectedMedia.id).subscribe(m => {
        this.media = m;
        this.coverImage = m.coverImage;
        this.mediaType = m.mediaType;
      });
  }

  createCreateForm() {
    if (this.mediaType.toLowerCase() === 'series') {
      this.form = this.fb.group({
        title: [null, Validators.compose([Validators.required])],
        coverImage: [null, Validators.compose([Validators.required])],
        genre: [null, Validators.compose([Validators.required])],
        synopsis: [null, Validators.compose([Validators.required])],
        broadcaster: [null, Validators.compose([Validators.required])],
      });
    }
  }

  createEditForm() {
    if (this.mediaType.toLowerCase() === 'series') {
      this.form = this.fb.group({
        title: [this.media.title, Validators.compose([Validators.required])],
        coverImage: [null, Validators.compose([Validators.required])],
        genre: [null, Validators.compose([Validators.required])],
        synopsis: [null, Validators.compose([Validators.required])],
        broadcaster: [null, Validators.compose([Validators.required])],
      });
    }
  }

  onSubmit() {
    if (this.mediaType.toLowerCase() === 'series') {
      const newSeries: SeriesDto = <SeriesDto>this.form.value;
      if (this.isCreate) {
        this.mediaService.createSeries(newSeries).subscribe(() => {
          this.router.navigate(['/home']);
        }, error => {
          this.snackBar.open('Error creating the Series.', 'Close', { duration: 3000 });
        });
      } else {
        this.mediaService.editSeries(this.mediaId, newSeries).subscribe(() => {
          this.router.navigate(['/home']);
        }, error => {
          this.snackBar.open('Error editing the Series.', 'Close', { duration: 3000 });
        });
      }
    } else if (this.mediaType.toLowerCase() === 'movie') {
      const newMovie: MovieDto = <MovieDto>this.form.value;
      if (this.isCreate) {
        this.mediaService.createMovie(newMovie).subscribe(() => {
          this.router.navigate(['/home']);
        }, error => {
          this.snackBar.open('Error creating the Movie.', 'Close', { duration: 3000 });
        });
      } else {
        this.mediaService.editMovie(this.mediaId, newMovie).subscribe(() => {
          this.router.navigate(['/home']);
        }, error => {
          this.snackBar.open('Error editing the Movie.', 'Close', { duration: 3000 });
        });
      }
    }
  }

  openEditMedia() {

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
