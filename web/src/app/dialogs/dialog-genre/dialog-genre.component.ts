import { GenreService } from './../../services/genre.service';
import { MatSnackBar, MAT_DIALOG_DATA, MatDialogRef } from '@angular/material';
import { Validators, FormGroup, FormBuilder } from '@angular/forms';
import { GenreDto } from './../../dto/genre-dto';
import { Component, OnInit, Inject } from '@angular/core';

@Component({
  selector: 'app-dialog-genre',
  templateUrl: './dialog-genre.component.html',
  styleUrls: ['./dialog-genre.component.scss']
})
export class DialogGenreComponent implements OnInit {

  edit: boolean;
  name: string;
  genreId: string;
  form: FormGroup;

  // tslint:disable-next-line:max-line-length
  constructor(private snackBar: MatSnackBar, private fb: FormBuilder, @Inject(MAT_DIALOG_DATA) public data: any, private genreService: GenreService, public dialogRef: MatDialogRef<DialogGenreComponent>) { }

  ngOnInit() {
    this.createForm();
    if (this.data.genre) {
      this.edit = true;
      this.genreId = this.data.genre.id;
    } else {
      this.edit = false;
    }
  }

  onSubmit() {
    if (this.edit) {
      const editedGenre: GenreDto = <GenreDto>this.form.value;
      this.genreService.edit(this.genreId, editedGenre).subscribe(result => {
        this.dialogRef.close('confirm');
      }, error => this.snackBar.open('There was an error when were trying to edit this genre.', 'Close', { duration: 3000 }));
    } else {
      const newGenre: GenreDto = <GenreDto>this.form.value;
      this.genreService.create(newGenre).subscribe(result => {
        this.dialogRef.close('confirm');
      }, error => this.snackBar.open('There was an error when were trying to create this genre.', 'Close', { duration: 3000 }));
    }
  }

  createForm() {
    if (this.data) {
      const editForm: FormGroup = this.fb.group({
        name: [this.data.genre.name, Validators.compose([Validators.required])]
      });
      this.form = editForm;
    } else {
      const newForm: FormGroup = this.fb.group({
        name: [null, Validators.compose([Validators.required])]
      });
      this.form = newForm;
    }
  }

  addGenre() {
    const genreCreateDto = new GenreDto(this.name);
    this.genreService.create(genreCreateDto).subscribe(
      genre => {
        this.dialogRef.close('confirm');
      }
    );
  }

  editGenre() {
    const genreEditDto = new GenreDto(this.name);
    this.genreService.edit(this.genreId, genreEditDto).subscribe(
      genre => {
        this.dialogRef.close('confirm');
      }
    );
  }

}
