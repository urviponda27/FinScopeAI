"use client"

import React, { useEffect, useRef } from "react";
import * as echarts from "echarts";

const CreditScoreChart = ({ data }) => {
  const chartRef = useRef(null);

  useEffect(() => {
    // Assuming the data is a single value representing the credit score
    const creditScore = data || 650; // Default to 650 if no data provided
    const remainingScore = 850 - creditScore; // Remaining part
    const creditScoreColor = creditScore >= 750 ? "#34D399" : creditScore >= 600 ? "#F59E0B" : "#EF4444";
    // Prepare data for the pie chart
    const chartData = [
      { name: "Credit Score", value: creditScore, color: creditScoreColor }, // Credit Score part
      { name: "Remaining", value: remainingScore, color: "#e5e7eb" }, // Remaining part
    ];

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
        type: "scroll",
        textStyle: {
          color: "#666",
        },
        selectedMode: false, // Disable toggling
      },
      series: [
        {
          name: "Credit Score Distribution",
          type: "pie",
          radius: ["50%", "65%"], // Thinner appearance
          avoidLabelOverlap: false,
          itemStyle: {
            borderRadius: 10,
            borderColor: "#fff",
            borderWidth: 2,
          },
          label: {
            show: false, // Hide labels on the chart itself
            position: "center", // Ensure label is positioned at the center
          },
          emphasis: {
            label: {
              show: false,
              fontSize: 16,
              fontWeight: "bold",
            },
          },
          labelLine: {
            show: false,
          },
          data: chartData.map((item) => ({
            value: item.value,
            name: item.name,
            itemStyle: {
              color: item.color,
            },
            emphasis: {
              itemStyle: {
                color: item.name === "Remaining" ? "#d1d5db" : item.color, // Make remaining color darker on hover
              },
            },
          })),
        },
      ],
      graphic: [
        {
          type: "text",
          left: "center",
          top: "center",
          style: {
            text: `${creditScore.toString()}\nCredit Score`, // Display the credit score
            textAlign: "center",
            fill: "#000", // Credit score color (indigo)
            font: "bold 20px Arial",
          },
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

  return <div ref={chartRef} className="w-full h-64" />; // Keep container size but make chart thinner
};

export default CreditScoreChart;
