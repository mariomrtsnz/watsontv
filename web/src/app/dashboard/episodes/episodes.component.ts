import { DialogDeleteEpisodeComponent } from './../../dialogs/dialog-delete-episode/dialog-delete-episode.component';
import { DialogEpisodeComponent } from './../../dialogs/dialog-episode/dialog-episode.component';
import { EpisodeService } from './../../services/episode.service';
import { Router } from '@angular/router';
import { Title } from '@angular/platform-browser';
import { MatPaginator, MatSnackBar, MatDialog, MatTableDataSource } from '@angular/material';
import { Component, OnInit, ViewChild } from '@angular/core';
import { OneEpisodeResponse } from 'src/app/interfaces/one-episode-response';
import { SeasonService } from 'src/app/services/season.service';
import { OneSeasonResponse } from 'src/app/interfaces/one-season-response';

@Component({
  selector: 'app-episodes',
  templateUrl: './episodes.component.html',
  styleUrls: ['./episodes.component.scss']
})
export class EpisodesComponent implements OnInit {
  displayedColumns: string[] = ['picture', 'name', 'actions'];
  dataSource;
  actors: any[] = [];
  alertMsg: string;
  selectedSeason: OneSeasonResponse;

  @ViewChild(MatPaginator) paginator: MatPaginator;

  constructor(private titleService: Title, private snackBar: MatSnackBar,
    private episodeService: EpisodeService, private router: Router, public dialog: MatDialog, private seasonService: SeasonService) { }

  ngOnInit() {
    this.titleService.setTitle('Episodes');
    console.log(this.seasonService.selectedSeason);
    this.selectedSeason = this.seasonService.selectedSeason;
    this.getAllEpisodes('Success retrieving items.');
  }

  getAllEpisodes(message: string) {
    this.seasonService.getAllEpisodes(this.selectedSeason.id).subscribe(receivedEpisodes => {
      this.dataSource = new MatTableDataSource(receivedEpisodes);
      this.dataSource.paginator = this.paginator;
    }, error => {
      this.snackBar.open('There was an error loading the data.', 'Close', { duration: 3000 });
    });
  }

  applyFilter(filterValue: string) {
    this.dataSource.filter = filterValue.trim().toLowerCase();
  }

  openDialogNewEpisode() {
    const newEpisodeDialog = this.dialog.open(DialogEpisodeComponent, { panelClass: 'add-dialog' });

    newEpisodeDialog.afterClosed().subscribe(result => {
      if (result === 'confirm') {
        this.alertMsg = 'Episode created';
        this.getAllEpisodes(this.alertMsg);
      }
    });
  }

  openDialogDeleteEpisode(actor: OneEpisodeResponse) {
    const deleteEpisodeDialog = this.dialog.open(DialogDeleteEpisodeComponent,
      { panelClass: 'delete-dialog', data: { actorId: actor.id, actorName: actor.name } });

      deleteEpisodeDialog.afterClosed().subscribe(result => {
      if (result === 'confirm') {
        this.alertMsg = 'Episode deleted';
        this.getAllEpisodes(this.alertMsg);
      }
    });

  }

  openDialogEditEpisode(actor: OneEpisodeResponse) {
    const updateEpisodeDialog = this.dialog.open(DialogEpisodeComponent, { panelClass: 'add-dialog', data: { actor: actor } });

    updateEpisodeDialog.afterClosed().subscribe(result => {
      if (result === 'confirm') {
        this.alertMsg = 'Actor updated';
        this.getAllEpisodes(this.alertMsg);
      }
    });
  }

}
