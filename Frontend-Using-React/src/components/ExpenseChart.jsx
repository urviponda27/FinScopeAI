"use client";

import { useEffect, useRef } from "react";
import * as echarts from "echarts";

const ExpenseChart = ({ data }) => {
  const chartRef = useRef(null);

  useEffect(() => {
    // Use provided data or fallback to mock data
    const chartData = data;

    // Show all companies without combining any into "Others"
    const processedData = chartData;

    // Initialize the chart
    const chart = echarts.init(chartRef.current);

    const option = {
      tooltip: {
        trigger: "item",
        formatter: "{b}: {c} ({d}%)",
      },
      legend: {
        orient: "vertical",
        right: 10,
        top: "center",
        type: "scroll", // enable scroll if legend exceeds container
        textStyle: {
          color: "#666",
        },
        selectedMode: false, // disables toggling
      },
      series: [
        {
          name: "Expense Type",
          type: "pie",
          radius: ["50%", "75%"],
          avoidLabelOverlap: false,
          itemStyle: {
            borderRadius: 10,
            borderColor: "#fff",
            borderWidth: 2,
          },
          label: {
            show: false,
            position: "center",
          },
          emphasis: {
            label: {
              show: true,
              fontSize: 16,
              fontWeight: "bold",
            },
          },
          labelLine: {
            show: false,
          },
          data: processedData.map((item, index) => {
            const colors = [
              "#6366f1", // indigo-500
              "#8b5cf6", // violet-500
              "#a855f7", // purple-500
              "#d946ef", // fuchsia-500
              "#4f46e5", // indigo-600
              "#7c3aed", // violet-600
              "#9333ea", // purple-600
              "#c026d3", // fuchsia-600
            ];
            return {
              value: item.count,
              name: item.type,
              itemStyle: {
                color: colors[index % colors.length],
              },
            };
          }),
        },
      ],
    };

    chart.setOption(option);

    const handleResize = () => {
      chart.resize();
    };

    window.addEventListener("resize", handleResize);

    return () => {
      chart.dispose();
      window.removeEventListener("resize", handleResize);
    };
  }, [data]);

  return <div ref={chartRef} className="w-full h-64" />;
};

export default ExpenseChart;
