#pragma warning disable CA1416

using System.Globalization;
using BruteForce;
using ComputeSharp;
using Serilog;
using Serilog.Formatting.Json;

Log.Logger = new LoggerConfiguration()
    .Enrich.FromLogContext()
    .WriteTo.Console()
    .WriteTo.File(new JsonFormatter(), "bruteforce.txt")
    .CreateLogger();

Log.Information("start");
int[] intcode = [/* omitted*/];

{
    for (var num = 42.0; false; num = Random.Shared.NextInt64(0, 1L << 54))
    {
        var array = Enumerable.Repeat(0.0, 40).ToArray();
        array[0] = num;
        using var buffer = GraphicsDevice.GetDefault().AllocateReadWriteBuffer(array);
        using var intbuffer = GraphicsDevice.GetDefault().AllocateReadOnlyBuffer(intcode);

        GraphicsDevice.GetDefault().For(1, new Compute(buffer, intbuffer));
        var actual = buffer.ToArray();

        var expected = CpuCompute.CalcLong(intcode, (long)num);

        if (!expected.SequenceEqual(actual))
        {
            Log.Information("Failed: Num: {num}, (cpu,gpu): {Array}", num, expected.Zip(actual));
        }
        else
        {
            Log.Information("Worked: {num}", num);
        }
    }
}

{
    for (var offset = 0L; offset < 1L << 50;)
    {
        try
        {
            var array = Enumerable.Range(0, Perftest.BruteForce.ThreadCount).Select(x => (double)offset + x).ToArray();
            using var buffer = GraphicsDevice.GetDefault().AllocateReadWriteBuffer(array);
            using var intbuffer = GraphicsDevice.GetDefault().AllocateReadOnlyBuffer(intcode);

            GraphicsDevice.GetDefault().For(buffer.Length, new Perftest.BruteForce(buffer, intbuffer));
            buffer.CopyTo(array);

            if (array.Any(x => x != 0))
            {
                Log.Information("{arr}", array.Where(x => x != 0));
                break;
            }

            Log.Information("failed {Offset:X}-{finish:X} ({percent:P})",
                offset,
                offset + Perftest.BruteForce.StepSize - 1,
                offset / (double)(1L << 50));

            offset += Perftest.BruteForce.StepSize;
        }
        catch (Exception ex)
        {
            Log.Error(ex, "gpu died :(");
            Thread.Sleep(5000);
        }
    }
}