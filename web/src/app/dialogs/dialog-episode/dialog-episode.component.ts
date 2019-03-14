import { EpisodeService } from './../../services/episode.service';
import { EpisodeDto } from './../../dto/episode-dto';
import { MatSnackBar, MAT_DIALOG_DATA, MatDialogRef } from '@angular/material';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Component, OnInit, Inject } from '@angular/core';
import { SeasonService } from 'src/app/services/season.service';

@Component({
  selector: 'app-dialog-episode',
  templateUrl: './dialog-episode.component.html',
  styleUrls: ['./dialog-episode.component.scss']
})
export class DialogEpisodeComponent implements OnInit {

  edit: boolean;
  name: string;
  synopsis: string;
  airTime: Date;
  duration: number;
  number: number;
  season: string;
  episodeId: string;
  form: FormGroup;

  // tslint:disable-next-line:max-line-length
  constructor(private snackBar: MatSnackBar, private fb: FormBuilder, @Inject(MAT_DIALOG_DATA) public data: any, private episodeService: EpisodeService, public dialogRef: MatDialogRef<DialogEpisodeComponent>, private seasonService: SeasonService) { }

  ngOnInit() {
    this.season = this.seasonService.selectedSeason.id;
    this.createForm();
    if (this.data) {
      this.edit = true;
      this.episodeId = this.data.episode.id;
    } else {
      this.edit = false;
    }
  }

  onSubmit() {
    if (this.edit) {
      const editedEpisode: EpisodeDto = <EpisodeDto>this.form.value;
      this.episodeService.edit(this.episodeId, editedEpisode).subscribe(result => {
        this.dialogRef.close('confirm');
      }, error => this.snackBar.open('There was an error when were trying to edit this Episode.', 'Close', { duration: 3000 }));
    } else {
      const newEpisode: EpisodeDto = <EpisodeDto>this.form.value;
      this.episodeService.create(newEpisode).subscribe(result => {
        this.dialogRef.close('confirm');
      }, error => this.snackBar.open('There was an error when were trying to create this Episode.', 'Close', { duration: 3000 }));
    }
  }

  createForm() {
    if (this.data) {
      const editForm: FormGroup = this.fb.group({
        name: [this.data.episode.name, Validators.compose([Validators.required])],
        synopsis: [this.data.episode.synopsis, Validators.compose([Validators.required])],
        airTime: [this.data.episode.airTime, Validators.compose([Validators.required])],
        duration: [this.data.episode.duration, Validators.compose([Validators.required])],
        number: [this.data.episode.number, Validators.compose([Validators.required])],
        season: [this.data.episode.number, Validators.compose([Validators.required])]
      });
      this.form = editForm;
    } else {
      const newForm: FormGroup = this.fb.group({
        name: [null, Validators.compose([Validators.required])],
        synopsis: [null, Validators.compose([Validators.required])],
        airTime: [null, Validators.compose([Validators.required])],
        duration: [null, Validators.compose([Validators.required])],
        number: [null, Validators.compose([Validators.required])],
        season: [this.season, Validators.compose([Validators.required])]
      });
      this.form = newForm;
    }
  }

}
