import { HttpClient } from "@angular/common/http";
import { Component } from "@angular/core";
import { Chart } from "chart.js/auto";

interface Data {
    count: number;
    date: {
        month: number,
        year: number
    };
}

@Component({
    selector: 'bar-chart',
    templateUrl: './bar-chart.component.html',
    styleUrl: './bar-chart.component.css',
})
export class BarChartComponent {

    public chart?: Chart;

    constructor(private http: HttpClient) { }

    ngOnInit(): void {
        this.fetchData();
    }

    private fetchData() {
        this.http.get<Data[]>("/api/concerts/per-month")
            .subscribe((response: Data[]) => {
                const months = Array.from(response).map(x => new Date(x.date.year, (x.date.month - 1)).toLocaleString('default', { month: 'long' }));
                const data = Array.from(response).map((x) => x.count);

                this.chart = this.initializeChart(months, data);
            })
    }

    private initializeChart(months: string[], data: number[]): Chart {
        return new Chart("chart", {
            type: "bar",
            data: {
                labels: months,
                datasets: [{
                    label: '# of concerts',
                    data: data,
                    borderWidth: 1
                }]
            },
            options: {
                scales: {
                    y: {
                        beginAtZero: true
                    }
                }
            }
        })
    }

}
