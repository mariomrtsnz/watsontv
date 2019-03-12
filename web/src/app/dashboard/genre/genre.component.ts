import { DialogDeleteGenreComponent } from './../../dialogs/dialog-delete-genre/dialog-delete-genre.component';
import { DialogGenreComponent } from './../../dialogs/dialog-genre/dialog-genre.component';
import { GenreService } from './../../services/genre.service';
import { Router } from '@angular/router';
import { Component, OnInit, ViewChild } from '@angular/core';
import { MatPaginator, MatSnackBar, MatTableDataSource, MatDialog } from '@angular/material';
import { Title } from '@angular/platform-browser';
import { OneGenreResponse } from 'src/app/interfaces/one-genre-response';

@Component({
  selector: 'app-genre',
  templateUrl: './genre.component.html',
  styleUrls: ['./genre.component.scss']
})
export class GenreComponent implements OnInit {

  displayedColumns: string[] = ['name', 'actions'];
  dataSource;
  genres: any[] = [];
  alertMsg: string;

  @ViewChild(MatPaginator) paginator: MatPaginator;

  constructor(private titleService: Title, private snackBar: MatSnackBar,
    private genreService: GenreService, private router: Router, public dialog: MatDialog) { }

  ngOnInit() {
    this.titleService.setTitle('Genres');
    this.getAllGenres('Success retrieving items.');
  }

  getAllGenres(message: string) {
    this.genreService.getAll().subscribe(receivedGenres => {
      this.dataSource = new MatTableDataSource(receivedGenres.rows);
      this.dataSource.paginator = this.paginator;
    }, error => {
      this.snackBar.open('There was an error loading the data.', 'Close', { duration: 3000 });
    });
  }

  applyFilter(filterValue: string) {
    this.dataSource.filter = filterValue.trim().toLowerCase();
  }

  openDialogNewGenre() {
    const newGenreDialog = this.dialog.open(DialogGenreComponent, { panelClass: 'add-dialog' });

    newGenreDialog.afterClosed().subscribe(result => {
      if (result === 'confirm') {
        this.alertMsg = 'Genre created';
        this.getAllGenres(this.alertMsg);
      }
    });
  }

  // getArrayPois(b: OneGenreResponse) {
  //   const firstTwoPois: OnePoiResponse[] = [];
  //   for (let index = 0; index < 2; index++) {
  //     if (b.pois[index] != null) {
  //       firstTwoPois.push(b.pois[index]);
  //     }
  //   }
  //   return firstTwoPois;
  // }

  openDialogDeleteGenre(genre: OneGenreResponse) {
    const deleteGenreDialog = this.dialog.open(DialogDeleteGenreComponent,
      { panelClass: 'delete-dialog', data: { genreId: genre.id, genreName: genre.name } });

      deleteGenreDialog.afterClosed().subscribe(result => {
      if (result === 'confirm') {
        this.alertMsg = 'Genre deleted';
        this.getAllGenres(this.alertMsg);
      }
    });

  }

  openDialogEditGenre(genre: OneGenreResponse) {
    const updateGenreDialog = this.dialog.open(DialogGenreComponent, { panelClass: 'add-dialog', data: { genre: genre } });

    updateGenreDialog.afterClosed().subscribe(result => {
      if (result === 'confirm') {
        this.alertMsg = 'Genre updated';
        this.getAllGenres(this.alertMsg);
      }
    });
  }


}
