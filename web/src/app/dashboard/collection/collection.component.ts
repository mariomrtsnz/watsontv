import { DialogDeleteCollectionComponent } from './../../dialogs/dialog-delete-collection/dialog-delete-collection.component';
import { DialogCollectionComponent } from './../../dialogs/dialog-collection/dialog-collection.component';
import { CollectionService } from './../../services/collection.service';
import { Router } from '@angular/router';
import { Title } from '@angular/platform-browser';
import { MatPaginator, MatSnackBar, MatDialog, MatTableDataSource } from '@angular/material';
import { Component, OnInit, ViewChild } from '@angular/core';
import { OneCollectionResponse } from 'src/app/interfaces/one-collection-response';

@Component({
  selector: 'app-collection',
  templateUrl: './collection.component.html',
  styleUrls: ['./collection.component.scss']
})
export class CollectionComponent implements OnInit {

  displayedColumns: string[] = ['name', 'description', 'owner', 'actions'];
  dataSource;
  collections: any[] = [];
  alertMsg: string;

  @ViewChild(MatPaginator) paginator: MatPaginator;

  constructor(private titleService: Title, private snackBar: MatSnackBar,
    private collectionService: CollectionService, private router: Router, public dialog: MatDialog) { }

  ngOnInit() {
    this.titleService.setTitle('Collections');
    this.getAllGenres('Success retrieving items.');
  }

  getAllGenres(message: string) {
    this.collectionService.getAll().subscribe(receivedCollections => {
      this.dataSource = new MatTableDataSource(receivedCollections.rows);
      this.dataSource.paginator = this.paginator;
    }, error => {
      this.snackBar.open('There was an error loading the data.', 'Close', { duration: 3000 });
    });
  }

  applyFilter(filterValue: string) {
    this.dataSource.filter = filterValue.trim().toLowerCase();
  }

  openDialogNewGenre() {
    const newGenreDialog = this.dialog.open(DialogCollectionComponent, { panelClass: 'add-dialog' });

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

  openDialogDeleteGenre(collection: OneCollectionResponse) {
    const deleteGenreDialog = this.dialog.open(DialogDeleteCollectionComponent,
      { panelClass: 'delete-dialog', data: { collectionId: collection.id, collectionName: collection.name } });

      deleteGenreDialog.afterClosed().subscribe(result => {
      if (result === 'confirm') {
        this.alertMsg = 'Genre deleted';
        this.getAllGenres(this.alertMsg);
      }
    });

  }

  openDialogEditGenre(collection: OneCollectionResponse) {
    const updateGenreDialog = this.dialog.open(DialogCollectionComponent, { panelClass: 'add-dialog', data: { collection: collection } });

    updateGenreDialog.afterClosed().subscribe(result => {
      if (result === 'confirm') {
        this.alertMsg = 'Genre updated';
        this.getAllGenres(this.alertMsg);
      }
    });
  }

}
