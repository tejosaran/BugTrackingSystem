import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'filterByStatus',
  standalone: true
})
export class FilterByStatusPipe implements PipeTransform {
  transform(tickets: any[], status: string): any[] {
    if (!tickets) return [];
    if (!status) return tickets;
    return tickets.filter(
      t => t.status?.toLowerCase().trim() === status.toLowerCase().trim()
    );
  }
}
