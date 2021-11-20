Classes

Topic{
    partitions : {"0":[0,1,3]};
}

Partition{
        Topic topic;
        id;
        replica;
        last_comitted_offset;
        Set<Partition> isr;
        saveToDisk();
        sendOffsetToReplica();
        writeToLog()

}

Broker{
    partition[];
    goDown()
    goUp();
}

Controller{

}