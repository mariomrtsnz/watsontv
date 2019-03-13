import { Title } from '@angular/platform-browser';
import { Router } from '@angular/router';
import { MatDialog, MatSnackBar } from '@angular/material';
import { GenreService } from './../../services/genre.service';
import { MediaService } from './../../services/media.service';
import { Validators, FormBuilder, FormGroup } from '@angular/forms';
import { Component, OnInit } from '@angular/core';
import { OneMediaResponse } from 'src/app/interfaces/one-media-response';
import { SeriesDto } from 'src/app/dto/series-dto';
import { MovieDto } from 'src/app/dto/movie-dto';
import { OneGenreResponse } from 'src/app/interfaces/one-genre-response';

@Component({
  selector: 'app-media-form',
  templateUrl: './media-form.component.html',
  styleUrls: ['./media-form.component.scss']
})
export class MediaFormComponent implements OnInit {

  isCreate = false;
  isEdit = false;
  media: OneMediaResponse;
  mediaType: string;
  form: FormGroup;
  allGenres: OneGenreResponse[];
  allMediaTypes: ['Series', 'Movie'];
  urlImage: string;

  constructor(private fb: FormBuilder, private mediaService: MediaService, private genreService: GenreService,
    public router: Router, public snackBar: MatSnackBar, private titleService: Title, public dialog: MatDialog) { }

  ngOnInit() {
    if (this.router.url.indexOf('/home/media/create') > -1) {
      if (!this.mediaService.mediaType) {
        this.router.navigate(['/home']);
      }
      this.isCreate = true;
      this.mediaType = this.mediaService.mediaType;
      this.titleService.setTitle('Create - Media');
      this.createCreateForm();
    } else if (this.router.url.indexOf('/home/media/edit') > -1) {
      this.isEdit = true;
      if (!this.mediaService.selectedMedia) {
        this.router.navigate(['/home']);
      }
      this.media = this.mediaService.selectedMedia;
      this.createEditForm();
      this.titleService.setTitle('Edit - Media');
      this.getData();
    }
    this.getGenres();
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
    } else {
      this.form = this.fb.group({
        title: [null, Validators.compose([Validators.required])],
        coverImage: [null, Validators.compose([Validators.required])],
        genre: [null, Validators.compose([Validators.required])],
        synopsis: [null, Validators.compose([Validators.required])],
        trailer: [null, Validators.compose([Validators.required])],
      });
    }
  }

  createEditForm() {
    if (this.media.mediaType.toLowerCase() === 'series') {
      this.form = this.fb.group({
        title: [this.media.title, Validators.compose([Validators.required])],
        coverImage: [this.media.coverImage, Validators.compose([Validators.required])],
        genre: [this.media.genre, Validators.compose([Validators.required])],
        synopsis: [this.media.synopsis, Validators.compose([Validators.required])],
        broadcaster: [this.media.broadcaster, Validators.compose([Validators.required])],
      });
    } else {
      this.form = this.fb.group({
        title: [this.media.title, Validators.compose([Validators.required])],
        coverImage: [this.media.coverImage, Validators.compose([Validators.required])],
        genre: [this.media.genre, Validators.compose([Validators.required])],
        synopsis: [this.media.synopsis, Validators.compose([Validators.required])],
        trailer: [null, Validators.compose([Validators.required])],
      });
    }
  }

  getGenres() {
    this.genreService.getAll()
      .subscribe(genres => this.allGenres = genres.rows);
  }

  getData() {
    this.mediaService.getOne(this.mediaService.selectedMedia.id).subscribe(m => {
      this.media = m;
    });
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
        this.mediaService.editSeries(this.media.id, newSeries).subscribe(() => {
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
        this.mediaService.editMovie(this.media.id, newMovie).subscribe(() => {
          this.router.navigate(['/home']);
        }, error => {
          this.snackBar.open('Error editing the Movie.', 'Close', { duration: 3000 });
        });
      }
    }
  }

  imageUpload() {

  }

}
