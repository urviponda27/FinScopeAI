"use client";

import React, { useEffect, useRef } from "react";
import * as echarts from "echarts";

const PredictedCreditScoreChart = ({ suggestionData, creditScore }) => {
  const chartRef = useRef(null);

  useEffect(() => {
    const chart = echarts.init(chartRef.current);

    const getShortLabel = (text) =>
      text.length > 20 ? text.slice(0, 20) + "…" : text;

    const creditScoreColor =
      creditScore >= 750 ? "#34D399" : creditScore >= 600 ? "#F59E0B" : "#EF4444";

    const chartData = [
      {
        value: creditScore,
        name: "Credit Score",
        itemStyle: { color: creditScoreColor },
      },
      ...suggestionData.map((item) => ({
        value: item.value,
        name: getShortLabel(item.title),
        itemStyle: { color: item.color },
      })),
    ];

    const initialSelection = {
      "Credit Score": true,
    };
    suggestionData.forEach((item) => {
      initialSelection[getShortLabel(item.title)] = false;
    });

    const getTotalScore = (selected) => {
      return suggestionData.reduce((acc, item) => {
        const short = getShortLabel(item.title);
        if (selected[short]) {
          return acc + item.value;
        }
        return acc;
      }, creditScore);
    };

    const getRemainingScore = (selected) => {
      return 850 - getTotalScore(selected);
    };

    const getCenterLabel = (selected) => {
      const total = getTotalScore(selected);
      return `{score|${total}}\nCredit Score`;
    };

    const option = {
      tooltip: {
        trigger: "item",
        formatter: "{b}: {c} ({d}%)",
      },
      legend: {
        orient: "vertical",
        right: 10,
        top: "center",
        selected: initialSelection,
        data: suggestionData.map((item) => getShortLabel(item.title)),
      },
      series: [
        {
          name: "Credit Score Analysis",
          type: "pie",
          radius: ["50%", "65%"],
          avoidLabelOverlap: false,
          itemStyle: {
            borderRadius: 10,
            borderColor: "#fff",
            borderWidth: 2,
          },
          label: {
            show: true,
            position: "center",
            formatter: getCenterLabel(initialSelection),
            rich: {
              score: {
                fontSize: 20,
                fontWeight: "bold",
              },
              label: {
                fontSize: 14,
                color: "#999",
              },
            },
          },
          labelLine: {
            show: false,
          },
          data: [
            ...chartData,
            {
              value: getRemainingScore(initialSelection),
              name: "Remaining Score",
              itemStyle: { color: "#e5e7eb" },
            },
          ],
        },
      ],
    };

    chart.setOption(option);

    chart.on("legendselectchanged", (e) => {
      const selected = e.selected;
      const total = getTotalScore(selected);
      const remainingScore = 850 - total;

      chart.setOption({
        series: [
          {
            label: {
              formatter: `{score|${total}}\nCredit Score`,
            },
            data: [
              ...chartData,
              {
                value: remainingScore,
                name: "Remaining Score",
                itemStyle: { color: "#e5e7eb" },
              },
            ],
          },
        ],
      });
    });

    window.addEventListener("resize", chart.resize);

    return () => {
      chart.dispose();
      window.removeEventListener("resize", chart.resize);
    };
  }, [creditScore]);

  return (
    <div className="space-y-4">
      <div ref={chartRef} className="w-full h-64" />
      <div className="max-h-64 overflow-y-auto space-y-3 p-2 bg-white rounded shadow">
        {suggestionData.map((item, idx) => (
          <div key={idx} className="border-b pb-2">
            <div className="flex items-center gap-2 mb-1">
              <span
                className="inline-block w-3 h-3 rounded-full"
                style={{ backgroundColor: item.color }}
              ></span>
              <span className="font-medium text-sm">{item.title}</span>
              <span className="text-xs bg-gray-100 px-2 py-0.5 rounded ml-auto">
                +{item.value} pts
              </span>
            </div>
            <p className="text-gray-600 text-sm">{item.description}</p>
          </div>
        ))}
      </div>
    </div>
  );
};

export default PredictedCreditScoreChart;
